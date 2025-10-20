package com.example.soratech.repository;

import com.example.soratech.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByDeletedFalse(Pageable pageable);

    Page<Product> findAllByDeletedTrue(Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndDeletedFalse(String name, Pageable pageable);

    Page<Product> findByCategoryIdAndDeletedFalse(Long categoryId, Pageable pageable);

    Page<Product> findByManufacturerIdAndDeletedFalse(Long manufacturerId, Pageable pageable);

    Page<Product> findBySuppliersIdAndDeletedFalse(Long supplierId, Pageable pageable);
    
    Page<Product> findByPriceBetweenAndDeletedFalse(Double minPrice, Double maxPrice, Pageable pageable);
    
    Page<Product> findByPriceGreaterThanEqualAndDeletedFalse(Double minPrice, Pageable pageable);
    
    Page<Product> findByPriceLessThanEqualAndDeletedFalse(Double maxPrice, Pageable pageable);
}