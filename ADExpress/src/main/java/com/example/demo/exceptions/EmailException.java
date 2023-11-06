package com.example.demo.exceptions;

public abstract class EmailException extends RuntimeException{

    public EmailException(String message) {
        super(message);
    }
}
