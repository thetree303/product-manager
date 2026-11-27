package com.example.productmanager.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class NotZeroValidator implements ConstraintValidator<NotZero, Number> {
    @Override
    public boolean isValid(
            Number value,
            ConstraintValidatorContext context
    ) {
        if (value == null) return true;

        return new BigDecimal(value.toString()).compareTo(new BigDecimal("0")) != 0;
    }
}
