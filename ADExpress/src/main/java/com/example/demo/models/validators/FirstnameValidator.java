package com.example.demo.models.validators;

import com.example.demo.models.annotations.Firstname;
import com.example.demo.utils.ValidatorUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FirstnameValidator implements ConstraintValidator<Firstname,CharSequence> {

    private int minLength;
    private int maxLength;

    @Override
    public void initialize(Firstname constraintAnnotation) {
        maxLength = constraintAnnotation.maxLength();
        minLength = constraintAnnotation.minLength();
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        String firstname = charSequence.toString();
        int firstNameLength = firstname.length();

        if(firstNameLength < minLength){
            ValidatorUtils.setErrorMessage(constraintValidatorContext,"Firstname is too short");
            return false;
        }
        if(firstNameLength > maxLength){
            ValidatorUtils.setErrorMessage(constraintValidatorContext,"Firstname is too long");
            return false;
        }
        return true;
    }
}
