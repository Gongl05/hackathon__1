package com.oreo.config;

import com.oreo.entity.Sale;
import com.oreo.repository.SaleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class SaleDataLoader implements CommandLineRunner {

    private final SaleRepository repo;

    public SaleDataLoader(SaleRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            repo.saveAll(List.of(
                    new Sale("Lima", "Oreo Vainilla", 100, 250.0, LocalDateTime.now().minusDays(1)),
                    new Sale("Lima", "Oreo Chocolate", 150, 375.0, LocalDateTime.now().minusDays(2)),
                    new Sale("Cusco", "Oreo Doble Crema", 200, 600.0, LocalDateTime.now().minusDays(3)),
                    new Sale("Piura", "Oreo Fresa", 80, 200.0, LocalDateTime.now().minusDays(4)),
                    new Sale("Lima", "Oreo Menta", 50, 125.0, LocalDateTime.now().minusDays(5))
            ));
        }
    }
}