package com.oreo.dto;
import java.time.LocalDateTime;

public record SaleRequest (String branch,
                          String product,
                          Integer quantity,
                          Double totalAmount,
                          LocalDateTime saleDate){}
