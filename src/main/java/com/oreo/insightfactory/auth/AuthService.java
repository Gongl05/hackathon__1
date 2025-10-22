package com.oreo.insightfactory.auth;

import com.oreo.insightfactory.auth.dto.*;
import com.oreo.insightfactory.user.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
  private final UserRepository users;
  private final PasswordEncoder encoder;
  private final JwtUtil jwt;

  public AuthService(UserRepository users, PasswordEncoder encoder, JwtUtil jwt) {
    this.users = users; this.encoder = encoder; this.jwt = jwt;
  }

  @Transactional
  public UserResponse register(RegisterRequest req) {
    if (users.existsByUsername(req.username())) throw new IllegalArgumentException("username ya existe");
    if (users.existsByEmail(req.email())) throw new IllegalArgumentException("email ya existe");

    if (req.role() == Role.CENTRAL && req.branch() != null)
      throw new IllegalArgumentException("branch debe ser null para CENTRAL");
    if (req.role() == Role.BRANCH && (req.branch() == null || req.branch().isBlank()))
      throw new IllegalArgumentException("branch es obligatorio para BRANCH");

    User u = new User();
    u.setUsername(req.username());
    u.setEmail(req.email());
    u.setPasswordHash(encoder.encode(req.password()));
    u.setRole(req.role());
    u.setBranch(req.role()==Role.CENTRAL ? null : req.branch());
    users.save(u);

    return new UserResponse("u_" + u.getId(), u.getUsername(), u.getEmail(),
      u.getRole().name(), u.getBranch(), u.getCreatedAt());
  }

  public AuthResponse login(LoginRequest req) {
    User u = users.findByUsername(req.username())
      .orElseThrow(() -> new IllegalArgumentException("credenciales inválidas"));
    if (!encoder.matches(req.password(), u.getPasswordHash()))
      throw new IllegalArgumentException("credenciales inválidas");

    String token = jwt.generate(u.getUsername(), u.getRole().name(), u.getBranch());
    return new AuthResponse(token, 3600, u.getRole().name(), u.getBranch());
  }
}