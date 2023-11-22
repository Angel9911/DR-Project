package com.example.demo.services.Impl;

import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.User_account;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.RolesRepository;
import com.example.demo.repositories.UserAccountRepository;
import com.example.demo.services.JwtAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class JwtAuthServiceImpl implements JwtAuthService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    UserAccountRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Transactional
    public void isUserExisting(User_account user_account, @RequestBody Customer signupRequest) {

        customerRepository.updateCustomerAccountId(user_account.getUser_account_id(), signupRequest.getEmail(), signupRequest.getPhone());
    }

}
