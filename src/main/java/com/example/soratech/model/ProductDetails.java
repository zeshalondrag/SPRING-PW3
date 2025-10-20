package com.example.soratech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "product_details")
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "Продукт обязателен")
    private Product product;

    @Size(max = 1000, message = "Технические характеристики не должны превышать 1000 символов")
    @Column
    private String technicalSpecs;

    @Size(max = 500, message = "Гарантия не должна превышать 500 символов")
    @Column
    private String warrantyInfo;

    @Column(nullable = false)
    private boolean deleted = false;

    public ProductDetails() {
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getTechnicalSpecs() {
        return technicalSpecs;
    }

    public void setTechnicalSpecs(String technicalSpecs) {
        this.technicalSpecs = technicalSpecs;
    }

    public String getWarrantyInfo() {
        return warrantyInfo;
    }

    public void setWarrantyInfo(String warrantyInfo) {
        this.warrantyInfo = warrantyInfo;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}