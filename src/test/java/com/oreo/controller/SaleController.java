package com.oreo.controller;

import com.oreo.dto.*;
import com.oreo.service.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    // Simulación de obtención de la sucursal del usuario autenticado
    private String getUserBranch() {
        return "Lima"; // se reemplazaría por SecurityContextHolder
    }

    @PostMapping
    public ResponseEntity<SaleResponse> create(@RequestBody SaleRequest req) {
        var res = service.createSale(req, getUserBranch());
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping
    public ResponseEntity<Page<SaleResponse>> list(
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getSales(branch, from, to, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id, getUserBranch()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id, getUserBranch());
        return ResponseEntity.noContent().build();
    }
}