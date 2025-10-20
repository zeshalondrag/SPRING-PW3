package com.example.soratech.repository;

import com.example.soratech.model.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Page<Manufacturer> findAllByDeletedFalse(Pageable pageable);

    Page<Manufacturer> findAllByDeletedTrue(Pageable pageable);

    Page<Manufacturer> findByNameContainingIgnoreCaseAndDeletedFalse(String name, Pageable pageable);
    
    boolean existsByNameAndDeletedFalse(String name);
    
    Page<Manufacturer> findByNameContainingIgnoreCaseAndDeletedTrue(String name, Pageable pageable);
}