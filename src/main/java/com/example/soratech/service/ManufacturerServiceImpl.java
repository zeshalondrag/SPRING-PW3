package com.example.soratech.service;

import com.example.soratech.model.Manufacturer;
import com.example.soratech.repository.ManufacturerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Page<Manufacturer> findAllActive(Pageable pageable) {
        return manufacturerRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Page<Manufacturer> findAllDeleted(Pageable pageable) {
        return manufacturerRepository.findAllByDeletedTrue(pageable);
    }

    @Override
    public Page<Manufacturer> searchByName(String query, Pageable pageable) {
        return manufacturerRepository.findByNameContainingIgnoreCaseAndDeletedFalse(query, pageable);
    }

    @Override
    public List<Manufacturer> findAllActive() {
        return manufacturerRepository.findAllByDeletedFalse(Pageable.unpaged()).getContent();
    }

    @Override
    public Manufacturer findById(Long id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Производитель с ID " + id + " не найден"));
    }

    @Override
    public void save(Manufacturer manufacturer) {
        manufacturer.setDeleted(false);
        manufacturerRepository.save(manufacturer);
    }

    @Override
    public void update(Long id, Manufacturer manufacturer) {
        Manufacturer existing = findById(id);
        existing.setName(manufacturer.getName());
        existing.setDescription(manufacturer.getDescription());
        manufacturerRepository.save(existing);
    }

    @Override
    public void logicDelete(Long id) {
        Manufacturer manufacturer = findById(id);
        manufacturer.setDeleted(true);
        manufacturerRepository.save(manufacturer);
    }

    @Override
    public void logicDeleteAllByIds(List<Long> ids) {
        List<Manufacturer> manufacturers = manufacturerRepository.findAllById(ids);
        manufacturers.forEach(manufacturer -> manufacturer.setDeleted(true));
        manufacturerRepository.saveAll(manufacturers);
    }

    @Override
    public void delete(Long id) {
        Manufacturer manufacturer = findById(id);
        manufacturerRepository.delete(manufacturer);
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        manufacturerRepository.deleteAllById(ids);
    }

    @Override
    public void restore(Long id) {
        Manufacturer manufacturer = findById(id);
        manufacturer.setDeleted(false);
        manufacturerRepository.save(manufacturer);
    }
    
    @Override
    public boolean existsByName(String name) {
        return manufacturerRepository.existsByNameAndDeletedFalse(name);
    }
    
    @Override
    public Page<Manufacturer> searchDeletedByName(String query, Pageable pageable) {
        return manufacturerRepository.findByNameContainingIgnoreCaseAndDeletedTrue(query, pageable);
    }
}