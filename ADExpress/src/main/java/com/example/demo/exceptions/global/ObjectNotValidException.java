package com.example.demo.exceptions.global;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "object not valid")
public class ObjectNotValidException extends RuntimeException{

    private final String message;

    public ObjectNotValidException(String message) {
        super("object "+ message + " not valid");
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
