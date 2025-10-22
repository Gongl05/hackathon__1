package com.oreo.insightfactory.auth.dto;

import com.oreo.insightfactory.user.Role;
import jakarta.validation.constraints.*;

public record RegisterRequest(
  @Pattern(regexp = "[A-Za-z0-9_.]{3,30}", message = "username inválido (3-30, alfanumérico, _ y .)")
  String username,
  @Email(message = "email inválido")
  String email,
  @Size(min = 8, message = "password mínimo 8 caracteres")
  String password,
  @NotNull(message = "role requerido")
  Role role,
  String branch
) {}