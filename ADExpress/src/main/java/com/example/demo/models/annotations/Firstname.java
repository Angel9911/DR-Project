package com.example.demo.models.annotations;

import com.example.demo.constants.ValidationConstraints;
import com.example.demo.models.validators.FirstnameValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FirstnameValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Firstname {
    int minLength() default ValidationConstraints.FIRSTNAME_MIN_LENGTH;
    int maxLength() default ValidationConstraints.FIRSTNAME_MAX_LENGTH;
}
