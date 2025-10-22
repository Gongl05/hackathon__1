package com.oreo.insightfactory.auth.dto;

public record AuthResponse(String token, long expiresIn, String role, String branch) {}