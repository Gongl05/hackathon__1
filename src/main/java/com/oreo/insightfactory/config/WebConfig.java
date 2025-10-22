package com.oreo.insightfactory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Value("${app.cors.allowed-origins:*}") private String origins;
  @Value("${app.cors.allowed-methods:*}") private String methods;
  @Value("${app.cors.allowed-headers:*}") private String headers;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins(origins.split(","))
      .allowedMethods(methods.split(","))
      .allowedHeaders(headers.split(","));
  }
}