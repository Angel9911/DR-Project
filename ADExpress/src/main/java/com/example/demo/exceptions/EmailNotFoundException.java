package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Email not found")
public class EmailNotFoundException extends EmailException{
    public EmailNotFoundException(String message) {
        super(message);
    }
}
