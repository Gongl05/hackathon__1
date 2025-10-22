package com.oreo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String branch;
    private String product;
    private int quantity;
    private double totalAmount;
    private LocalDateTime saleDate;

    public Sale() {}

    public Sale(String branch, String product, int quantity, double totalAmount, LocalDateTime saleDate) {
        this.branch = branch;
        this.product = product;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.saleDate = saleDate;
    }

    public Long getId() { return id; }
    public String getBranch() { return branch; }
    public String getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getTotalAmount() { return totalAmount; }
    public LocalDateTime getSaleDate() { return saleDate; }

    public void setId(Long id) { this.id = id; }
    public void setBranch(String branch) { this.branch = branch; }
    public void setProduct(String product) { this.product = product; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setSaleDate(LocalDateTime saleDate) { this.saleDate = saleDate; }
}