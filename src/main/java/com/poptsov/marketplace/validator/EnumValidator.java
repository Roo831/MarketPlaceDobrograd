package com.poptsov.marketplace.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    private Enum<?>[] enumValues;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        enumValues = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        System.out.println("Validating enum value: " + value);
        if (value == null) {
            return false;
        }
        for (Enum<?> enumValue : enumValues) {
            if (enumValue.equals(value)) {
                return true;
            }
        }
        System.out.println("isValid return false");
        return false;
    }
}