package com.example.soratech.service;

import com.example.soratech.model.Product;
import com.example.soratech.model.ProductDetails;
import com.example.soratech.repository.ProductRepository;
import com.example.soratech.repository.ProductDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ProductDetailsRepository productDetailsRepository;
    private final ProductRepository productRepository;

    public ProductDetailsServiceImpl(ProductDetailsRepository productDetailsRepository,
                                    ProductRepository productRepository) {
        this.productDetailsRepository = productDetailsRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDetails> findAllActive(Pageable pageable) {
        return productDetailsRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Page<ProductDetails> findAllDeleted(Pageable pageable) {
        return productDetailsRepository.findAllByDeletedTrue(pageable);
    }

    @Override
    public ProductDetails findById(Long id) {
        return productDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Детали продукта с ID " + id + " не найдены"));
    }

    @Override
    public ProductDetails findByProductId(Long productId) {
        return productDetailsRepository.findByProductIdAndDeletedFalse(productId);
    }

    @Override
    public void save(ProductDetails productDetails, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Продукт с ID " + productId + " не найден"));
        productDetails.setProduct(product);
        productDetails.setDeleted(false);
        productDetailsRepository.save(productDetails);
    }

    @Override
    public void update(Long id, ProductDetails productDetails, Long productId) {
        ProductDetails existing = findById(id);
        existing.setTechnicalSpecs(productDetails.getTechnicalSpecs());
        existing.setWarrantyInfo(productDetails.getWarrantyInfo());
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Продукт с ID " + productId + " не найден"));
        existing.setProduct(product);
        productDetailsRepository.save(existing);
    }

    @Override
    public void logicDelete(Long id) {
        ProductDetails productDetails = findById(id);
        productDetails.setDeleted(true);
        productDetailsRepository.save(productDetails);
    }

    @Override
    public void logicDeleteAllByIds(List<Long> ids) {
        List<ProductDetails> productDetailsList = productDetailsRepository.findAllById(ids);
        productDetailsList.forEach(details -> details.setDeleted(true));
        productDetailsRepository.saveAll(productDetailsList);
    }

    @Override
    public void delete(Long id) {
        ProductDetails productDetails = findById(id);
        productDetailsRepository.delete(productDetails);
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        productDetailsRepository.deleteAllById(ids);
    }

    @Override
    public void restore(Long id) {
        ProductDetails productDetails = findById(id);
        productDetails.setDeleted(false);
        productDetailsRepository.save(productDetails);
    }
    
    @Override
    public Page<ProductDetails> searchByProductName(String query, Pageable pageable) {
        return productDetailsRepository.findByProductNameContainingIgnoreCaseAndDeletedFalse(query, pageable);
    }
    
    @Override
    public Page<ProductDetails> filterByProduct(Long productId, Pageable pageable) {
        return productDetailsRepository.findByProductIdAndDeletedFalse(productId, pageable);
    }
}