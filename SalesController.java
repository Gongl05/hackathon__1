package com.oreo.insights;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private final SalesAggregationService service;

    public SalesController(SalesAggregationService service) {
        this.service = service;
    }

    @PostMapping("/summary/weekly")
    public ResponseEntity<Void> triggerWeeklySummary() {
        service.generateWeeklySummary();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build(); // 202
    }
}
