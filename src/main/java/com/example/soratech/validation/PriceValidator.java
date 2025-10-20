package com.example.soratech.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class PriceValidator implements ConstraintValidator<ValidPrice, BigDecimal> {

    @Override
    public void initialize(ValidPrice constraintAnnotation) {
    }

    @Override
    public boolean isValid(BigDecimal price, ConstraintValidatorContext context) {
        if (price == null) {
            return false;
        }
        
        // Проверяем, что цена больше 0
        return price.compareTo(BigDecimal.ZERO) > 0;
    }
}

