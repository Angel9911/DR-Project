package com.example.demo.services;

import com.example.demo.models.User_account;

public interface UserAccountService {
    User_account IsUsernameExist(String username);
}
