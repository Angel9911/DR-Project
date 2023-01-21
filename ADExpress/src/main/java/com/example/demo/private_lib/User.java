package com.example.demo.private_lib;

import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;

@Component
public abstract class User {
    public abstract Object Login(String username) throws ValidationException;
    public abstract Object Update(Object object);
    public abstract void Insert(Object object);
}
