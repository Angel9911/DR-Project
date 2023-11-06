package com.example.demo.models.validators;

import com.example.demo.models.annotations.Email;
import com.example.demo.utils.ValidatorUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email,CharSequence> {

    private int maxLength;
    private int minLength;
    private Pattern pattern;


    @Override
    public void initialize(Email constraintAnnotation) {
        maxLength = constraintAnnotation.maxLength();
        minLength = constraintAnnotation.minLength();
        pattern = Pattern.compile(constraintAnnotation.regex());
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {

        if(charSequence == null){
            ValidatorUtils.setErrorMessage(constraintValidatorContext, " Email is empty");
            return false;
        }

        String email = charSequence.toString();
        int emailSize = email.length();

        if(emailSize < minLength){
            ValidatorUtils.setErrorMessage(constraintValidatorContext,"Email is too short");
            return false;
        }
        if(emailSize > maxLength){
            ValidatorUtils.setErrorMessage(constraintValidatorContext,"Email is too long");
            return false;
        }
        if(!pattern.matcher(email).matches()){
            ValidatorUtils.setErrorMessage(constraintValidatorContext,"Email does not match the template.");
            return false;
        }
        return true;
    }
}
