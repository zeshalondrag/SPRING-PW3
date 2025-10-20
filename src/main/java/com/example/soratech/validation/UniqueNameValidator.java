package com.example.soratech.validation;

import com.example.soratech.service.CategoryService;
import com.example.soratech.service.ManufacturerService;
import com.example.soratech.service.SupplierService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ManufacturerService manufacturerService;
    
    @Autowired
    private SupplierService supplierService;

    private String entityType;

    @Override
    public void initialize(UniqueName constraintAnnotation) {
        this.entityType = constraintAnnotation.entityType();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null || name.trim().isEmpty()) {
            return true; // Пустые значения обрабатываются другими валидаторами
        }
        
        try {
            switch (entityType.toLowerCase()) {
                case "category":
                    return !categoryService.existsByName(name);
                case "manufacturer":
                    return !manufacturerService.existsByName(name);
                case "supplier":
                    return !supplierService.existsByName(name);
                default:
                    return true;
            }
        } catch (Exception e) {
            // В случае ошибки базы данных, считаем валидацию пройденной
            return true;
        }
    }
}

