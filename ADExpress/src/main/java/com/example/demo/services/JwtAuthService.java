package com.example.demo.services;

import com.example.demo.models.Customer;
import com.example.demo.models.User_account;
import org.springframework.web.bind.annotation.RequestBody;

public interface JwtAuthService {
    void isUserExisting(User_account user_account, @RequestBody Customer signupRequest);
}
