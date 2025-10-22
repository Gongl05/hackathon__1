package com.oreo.service;

import com.oreo.dto.*;
import com.oreo.entity.Sale;
import com.oreo.mapper.SaleMapper;
import com.oreo.repository.SaleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SaleService {

    private final SaleRepository repo;
    private final SaleMapper mapper;

    public SaleService(SaleRepository repo, SaleMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public SaleResponse createSale(SaleRequest req, String userBranch) {
        if (!req.branch().equals(userBranch))
            throw new AccessDeniedException("No puedes registrar ventas en otra sucursal");

        Sale saved = repo.save(mapper.toEntity(req));
        return mapper.toResponse(saved);
    }

    public Page<SaleResponse> getSales(String branch, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return repo.findFiltered(branch, from, to, pageable)
                .map(mapper::toResponse);
    }

    public SaleResponse getById(Long id, String userBranch) {
        Sale s = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        if (!s.getBranch().equals(userBranch))
            throw new AccessDeniedException("Acceso denegado a esta sucursal");

        return mapper.toResponse(s);
    }

    public void deleteById(Long id, String userBranch) {
        Sale s = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        if (!s.getBranch().equals(userBranch))
            throw new AccessDeniedException("No puedes eliminar ventas de otra sucursal");

        repo.delete(s);
    }
}
