package com.oreo.insightfactory.util;

import com.oreo.insightfactory.auth.JwtPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
  public JwtPrincipal principal() {
    Authentication a = SecurityContextHolder.getContext().getAuthentication();
    if (a != null && a.getPrincipal() instanceof JwtPrincipal p) return p;
    return null;
  }
}