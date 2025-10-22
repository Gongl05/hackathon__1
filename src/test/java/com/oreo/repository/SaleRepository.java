package com.oreo.repository;

import com.oreo.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("""
        SELECT s FROM Sale s
        WHERE (:branch IS NULL OR s.branch = :branch)
        AND (:from IS NULL OR s.saleDate >= :from)
        AND (:to IS NULL OR s.saleDate <= :to)
    """)
    Page<Sale> findFiltered(String branch, LocalDateTime from, LocalDateTime to, Pageable pageable);
}