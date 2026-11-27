package com.example.productmanager.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotZeroValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotZero {
    String message() default "Value must not be zero";

    // Thuoc tinh bat buoc theo chuan bean validation
    Class<?>[] groups() default {};     // 	Hỗ trợ nhóm validation

    Class<? extends Payload>[] payload() default {};    // Metadata bổ sung cho validation
}
