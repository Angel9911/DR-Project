package com.example.demo.services;

import com.example.demo.models.entity.User_account;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;

@Service
public interface UserAccountService {

    Object Login(String username) throws ValidationException;
    void Insert(Object object);
}
