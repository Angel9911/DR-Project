package com.example.demo.models.annotations;

import com.example.demo.constants.ValidationConstraints;
import com.example.demo.models.validators.FirstnameValidator;
import com.example.demo.models.validators.LastnameValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LastnameValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Lastname {
    int maxLength() default ValidationConstraints.LASTNAME_MAX_LENGTH;
    int minLength() default ValidationConstraints.LASTNAME_MIN_LENGTH;
}
