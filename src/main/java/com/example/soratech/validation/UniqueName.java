package com.example.soratech.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueNameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueName {
    String message() default "Название уже используется";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String entityType() default "";
}

