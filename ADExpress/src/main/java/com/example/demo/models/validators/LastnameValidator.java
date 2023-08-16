package com.example.demo.models.validators;

import com.example.demo.models.annotations.Lastname;
import com.example.demo.utils.ValidatorUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastnameValidator implements ConstraintValidator<Lastname,CharSequence> {

    private int minLength;
    private int maxLength;

    @Override
    public void initialize(Lastname constraintAnnotation) {
        maxLength = constraintAnnotation.maxLength();
        minLength = constraintAnnotation.minLength();
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        String lastname = charSequence.toString();
        int lastNameLength = lastname.length();

        if(lastNameLength < minLength){
            ValidatorUtils.setErrorMessage(constraintValidatorContext,"Lastname is too short");
            return false;
        }
        if(lastNameLength > maxLength){
            ValidatorUtils.setErrorMessage(constraintValidatorContext,"Lastname is too long");
            return false;
        }
        return true;
    }
}
