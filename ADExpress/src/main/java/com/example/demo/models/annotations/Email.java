package com.example.demo.models.annotations;

import com.example.demo.constants.ValidationConstraints;
import com.example.demo.models.validators.EmailValidator;
import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

    int minLength() default ValidationConstraints.USER_EMAIL_MIN_LENGTH;

    int maxLength() default ValidationConstraints.USER_EMAIL_MAX_LENGTH;

    String regex() default ValidationConstraints.USER_EMAIl_REGEX;
}
