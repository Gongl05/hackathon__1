package com.oreo.insightfactory.auth;

public record JwtPrincipal(String username, String role, String branch) {}