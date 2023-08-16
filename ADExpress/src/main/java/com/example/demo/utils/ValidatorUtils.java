package com.example.demo.utils;

import javax.validation.ConstraintValidatorContext;

public final class ValidatorUtils {

    private ValidatorUtils(){

    }

    public static void setErrorMessage(final ConstraintValidatorContext context, final String errorMessage){
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
    }
}
