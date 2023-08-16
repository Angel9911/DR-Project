package com.example.demo.models.validators;

import com.example.demo.models.annotations.Phone;
import com.example.demo.utils.ValidatorUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone,CharSequence> {

    private int minLength;
    private int maxLength;
    private Pattern pattern;


    @Override
    public void initialize(Phone constraintAnnotation) {
        minLength = constraintAnnotation.minLength();
        maxLength = constraintAnnotation.maxLength();
        pattern = Pattern.compile(constraintAnnotation.regex());
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {

        if(charSequence == null){
            return false;
        }

        String phone = charSequence.toString();
        int phoneLength = phone.length();

        if(phoneLength < minLength){
            ValidatorUtils.setErrorMessage(constraintValidatorContext,"Phone length is too short");
            return false;
        }
        if(phoneLength > maxLength){
            ValidatorUtils.setErrorMessage(constraintValidatorContext,"Phone length is too long");
            return false;
        }
        if(!pattern.matcher(phone).matches()){
            ValidatorUtils.setErrorMessage(constraintValidatorContext,"Phone does not match the template");
            return false;
        }
        return true;
    }
}
