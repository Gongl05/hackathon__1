package com.oreo.dto;

import java.time.LocalDateTime;


public record SaleResponse(
        Long id,
        String branch,
        String product,
        Integer quantity,
        Double totalAmount,
        LocalDateTime saleDate
) {}