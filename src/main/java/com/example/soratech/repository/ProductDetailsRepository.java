package com.example.soratech.repository;

import com.example.soratech.model.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {
    Page<ProductDetails> findAllByDeletedFalse(Pageable pageable);

    Page<ProductDetails> findAllByDeletedTrue(Pageable pageable);

    ProductDetails findByProductIdAndDeletedFalse(Long productId);
    
    Page<ProductDetails> findByProductNameContainingIgnoreCaseAndDeletedFalse(String productName, Pageable pageable);
    
    Page<ProductDetails> findByProductIdAndDeletedFalse(Long productId, Pageable pageable);
}