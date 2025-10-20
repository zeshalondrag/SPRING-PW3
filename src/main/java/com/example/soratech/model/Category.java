package com.example.soratech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.example.soratech.validation.UniqueName;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название категории не может быть пустым")
    @Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
    @UniqueName(message = "Название категории уже используется", entityType = "category")
    @Column(nullable = false, unique = true)
    private String name;

    @Size(max = 300, message = "Описание не должно превышать 300 символов")
    @Column
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    @Column(nullable = false)
    private boolean deleted = false;

    public Category() {
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}