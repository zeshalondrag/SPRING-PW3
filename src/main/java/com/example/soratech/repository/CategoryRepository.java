package com.example.soratech.repository;

import com.example.soratech.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByDeletedFalse(Pageable pageable);

    Page<Category> findAllByDeletedTrue(Pageable pageable);

    Page<Category> findByNameContainingIgnoreCaseAndDeletedFalse(String name, Pageable pageable);
    
    boolean existsByNameAndDeletedFalse(String name);
    
    Page<Category> findByNameContainingIgnoreCaseAndDeletedTrue(String name, Pageable pageable);
}