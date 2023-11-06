package com.example.demo.exceptions.global;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Object not found")
public class ObjectNotFoundException extends RuntimeException{

    private final String userMessage;

    public ObjectNotFoundException(String objectMessage) {
        super("object "+ objectMessage + " not found");

        this.userMessage = objectMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

}
