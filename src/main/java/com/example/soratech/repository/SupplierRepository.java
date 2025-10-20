package com.example.soratech.repository;

import com.example.soratech.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Page<Supplier> findAllByDeletedFalse(Pageable pageable);

    Page<Supplier> findAllByDeletedTrue(Pageable pageable);

    Page<Supplier> findByNameContainingIgnoreCaseAndDeletedFalse(String name, Pageable pageable);
    
    boolean existsByEmailAndDeletedFalse(String email);
    
    boolean existsByNameAndDeletedFalse(String name);
    
    Page<Supplier> findByNameContainingIgnoreCaseAndDeletedTrue(String name, Pageable pageable);
}