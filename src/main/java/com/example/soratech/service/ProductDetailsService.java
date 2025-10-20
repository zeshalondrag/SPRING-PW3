package com.example.soratech.service;

import com.example.soratech.model.ProductDetails;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDetailsService {
    Page<ProductDetails> findAllActive(Pageable pageable);

    Page<ProductDetails> findAllDeleted(Pageable pageable);

    ProductDetails findById(Long id);

    ProductDetails findByProductId(Long productId);

    void save(ProductDetails productDetails, Long productId);

    void update(Long id, ProductDetails productDetails, Long productId);

    void logicDelete(Long id);

    void logicDeleteAllByIds(List<Long> ids);

    void delete(Long id);

    void deleteAllByIds(List<Long> ids);

    void restore(Long id);
    
    Page<ProductDetails> searchByProductName(String query, Pageable pageable);
    
    Page<ProductDetails> filterByProduct(Long productId, Pageable pageable);
}