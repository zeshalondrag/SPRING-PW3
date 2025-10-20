package com.example.soratech.service;

import com.example.soratech.model.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ManufacturerService {
    Page<Manufacturer> findAllActive(Pageable pageable);

    Page<Manufacturer> findAllDeleted(Pageable pageable);

    Page<Manufacturer> searchByName(String query, Pageable pageable);

    List<Manufacturer> findAllActive();

    Manufacturer findById(Long id);

    void save(Manufacturer manufacturer);

    void update(Long id, Manufacturer manufacturer);

    void logicDelete(Long id);

    void logicDeleteAllByIds(List<Long> ids);

    void delete(Long id);

    void deleteAllByIds(List<Long> ids);

    void restore(Long id);
    
    boolean existsByName(String name);
    
    Page<Manufacturer> searchDeletedByName(String query, Pageable pageable);
}