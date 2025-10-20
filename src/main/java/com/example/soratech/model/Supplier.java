package com.example.soratech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.example.soratech.validation.UniqueEmail;
import com.example.soratech.validation.UniqueName;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название поставщика не может быть пустым")
    @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов")
    @UniqueName(message = "Название поставщика уже используется", entityType = "supplier")
    @Column(nullable = false, unique = true)
    private String name;

    @Email(message = "Email должен быть валидным")
    @NotBlank(message = "Email обязателен")
    @UniqueEmail(message = "Email уже используется")
    @Column(nullable = false)
    private String email;

    @Size(max = 500, message = "Контактная информация не должна превышать 500 символов")
    @Column
    private String contactInfo;

    @ManyToMany(mappedBy = "suppliers")
    private Set<Product> products = new HashSet<>();

    @Column(nullable = false)
    private boolean deleted = false;

    public Supplier() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
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