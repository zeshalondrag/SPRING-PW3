package com.example.soratech.service;

import com.example.soratech.model.Supplier;
import com.example.soratech.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Page<Supplier> findAllActive(Pageable pageable) {
        return supplierRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Page<Supplier> findAllDeleted(Pageable pageable) {
        return supplierRepository.findAllByDeletedTrue(pageable);
    }

    @Override
    public Page<Supplier> searchByName(String query, Pageable pageable) {
        return supplierRepository.findByNameContainingIgnoreCaseAndDeletedFalse(query, pageable);
    }

    @Override
    public List<Supplier> findAllActive() {
        return supplierRepository.findAllByDeletedFalse(Pageable.unpaged()).getContent();
    }

    @Override
    public Supplier findById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Поставщик с ID " + id + " не найден"));
    }

    @Override
    public void save(Supplier supplier) {
        supplier.setDeleted(false);
        supplierRepository.save(supplier);
    }

    @Override
    public void update(Long id, Supplier supplier) {
        Supplier existing = findById(id);
        existing.setName(supplier.getName());
        existing.setEmail(supplier.getEmail());
        existing.setContactInfo(supplier.getContactInfo());
        supplierRepository.save(existing);
    }

    @Override
    public void logicDelete(Long id) {
        Supplier supplier = findById(id);
        supplier.setDeleted(true);
        supplierRepository.save(supplier);
    }

    @Override
    public void logicDeleteAllByIds(List<Long> ids) {
        List<Supplier> suppliers = supplierRepository.findAllById(ids);
        suppliers.forEach(supplier -> supplier.setDeleted(true));
        supplierRepository.saveAll(suppliers);
    }

    @Override
    public void delete(Long id) {
        Supplier supplier = findById(id);
        supplierRepository.delete(supplier);
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        supplierRepository.deleteAllById(ids);
    }

    @Override
    public void restore(Long id) {
        Supplier supplier = findById(id);
        supplier.setDeleted(false);
        supplierRepository.save(supplier);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return supplierRepository.existsByEmailAndDeletedFalse(email);
    }
    
    @Override
    public boolean existsByName(String name) {
        return supplierRepository.existsByNameAndDeletedFalse(name);
    }
    
    @Override
    public Page<Supplier> searchDeletedByName(String query, Pageable pageable) {
        return supplierRepository.findByNameContainingIgnoreCaseAndDeletedTrue(query, pageable);
    }
}