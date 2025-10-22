package com.oreo.insightfactory.config;

import com.oreo.insightfactory.user.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DevSeedConfig {

  @Bean
  public org.springframework.boot.CommandLineRunner seedCentral(UserRepository users, PasswordEncoder enc) {
    return args -> {
      if (!users.existsByUsername("oreo.admin")) {
        User u = new User();
        u.setUsername("oreo.admin");
        u.setEmail("admin@oreo.com");
        u.setPasswordHash(enc.encode("Oreo1234"));
        u.setRole(Role.CENTRAL);
        u.setBranch(null);
        users.save(u);
      }
    };
  }
}