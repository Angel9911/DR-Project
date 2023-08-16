package com.example.demo.models.views;

import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Roles;
import com.example.demo.models.entity.User_account;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

// projection
@Projection(name = "user_AccountView", types = User_account.class)
public interface User_accountView {
    String getUsername();

}
