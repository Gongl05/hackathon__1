package com.oreo.insightfactory.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
  private final Key key;
  private final long expirationSeconds;

  public JwtUtil(@Value("${security.jwt.secret}") String secret,
                 @Value("${security.jwt.expiration-seconds}") long expirationSeconds) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expirationSeconds = expirationSeconds;
  }

  public String generate(String username, String role, String branch) {
    Instant now = Instant.now();
    return Jwts.builder()
      .setSubject(username)
      .addClaims(Map.of("role", role, "branch", branch))
      .setIssuedAt(Date.from(now))
      .setExpiration(Date.from(now.plusSeconds(expirationSeconds)))
      .signWith(key, SignatureAlgorithm.HS256)
      .compact();
  }

  public Jws<Claims> parse(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
  }
}