package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "File not found")
public class FileNotFoundException extends EmailException{
    public FileNotFoundException(String message) {
        super(message);
    }
}
