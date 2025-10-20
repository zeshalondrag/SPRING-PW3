package com.example.soratech.validation;

import com.example.soratech.service.SupplierService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private SupplierService supplierService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            return true; // Пустые значения обрабатываются другими валидаторами
        }
        
        try {
            return !supplierService.existsByEmail(email);
        } catch (Exception e) {
            // В случае ошибки базы данных, считаем валидацию пройденной
            return true;
        }
    }
}

