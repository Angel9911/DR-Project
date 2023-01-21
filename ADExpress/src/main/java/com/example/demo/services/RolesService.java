package com.example.demo.services;

import com.example.demo.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RolesService {
    @Autowired
    RolesRepository rolesRepository;

    /* @Transactional
    public Optional<Roles> findRoleIdByRoleName(String role_description){
       return rolesRepository.findRole_idByRole_description(role_description);
    } */
}
