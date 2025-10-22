package com.oreo.mapper;

import com.oreo.dto.*;
import com.oreo.entity.Sale;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper {

    public Sale toEntity(SaleRequest dto) {
        return new Sale(dto.branch(), dto.product(), dto.quantity(), dto.totalAmount(), dto.saleDate());
    }

    public SaleResponse toResponse(Sale entity) {
        return new SaleResponse(
                entity.getId(),
                entity.getBranch(),
                entity.getProduct(),
                entity.getQuantity(),
                entity.getTotalAmount(),
                entity.getSaleDate()
        );
    }
}
