package com.oreo.insightfactory.auth;

import com.oreo.insightfactory.auth.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService service;
  public AuthController(AuthService service) { this.service = service; }

  @PostMapping("/register")
  public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest req) {
    var res = service.register(req);
    return ResponseEntity.status(HttpStatus.CREATED).body(res);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
    return ResponseEntity.ok(service.login(req));
  }
}