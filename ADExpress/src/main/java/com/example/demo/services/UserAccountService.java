package com.example.demo.services;

import com.example.demo.models.User_account;
import org.springframework.stereotype.Service;

@Service
public interface UserAccountService {
    User_account IsUsernameExist(String username);
}
