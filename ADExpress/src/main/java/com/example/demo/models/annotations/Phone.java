package com.example.demo.models.annotations;

import com.example.demo.constants.ValidationConstraints;
import com.example.demo.models.validators.EmailValidator;
import com.example.demo.models.validators.PhoneValidator;
import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {

    String message() default "Invalid phone";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int minLength() default ValidationConstraints.PHONE_MIN_LENGTH;
    int maxLength() default ValidationConstraints.PHONE_MAX_LENGTH;
    String regex() default ValidationConstraints.PHONE_REGEX;
}
