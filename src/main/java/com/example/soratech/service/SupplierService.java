package com.example.soratech.service;

import com.example.soratech.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierService {
    Page<Supplier> findAllActive(Pageable pageable);

    Page<Supplier> findAllDeleted(Pageable pageable);

    Page<Supplier> searchByName(String query, Pageable pageable);

    List<Supplier> findAllActive();

    Supplier findById(Long id);

    void save(Supplier supplier);

    void update(Long id, Supplier supplier);

    void logicDelete(Long id);

    void logicDeleteAllByIds(List<Long> ids);

    void delete(Long id);

    void deleteAllByIds(List<Long> ids);

    void restore(Long id);
    
    boolean existsByEmail(String email);
    
    boolean existsByName(String name);
    
    Page<Supplier> searchDeletedByName(String query, Pageable pageable);
}