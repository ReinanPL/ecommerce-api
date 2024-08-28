package com.compass.reinan.api_ecommerce.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {

    private static final String[] VALID_PROFILES = {"admin", "client"};

    @Override
    public void initialize(ValidRole constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return
            false;
        }

        for (String validProfile : VALID_PROFILES) {
            if (validProfile.equalsIgnoreCase(value)) {
                return true;
            }
        }

        return false;
    }
}