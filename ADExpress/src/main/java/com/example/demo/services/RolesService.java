package com.example.demo.services;

import com.example.demo.model.Customer;
import com.example.demo.model.Roles;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class RolesService {
    @Autowired
    RolesRepository rolesRepository;

    /* @Transactional
    public Optional<Roles> findRoleIdByRoleName(String role_description){
       return rolesRepository.findRole_idByRole_description(role_description);
    } */
}
