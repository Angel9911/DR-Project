package com.example.demo.model.jwt;

import java.util.List;

public class JwtResponse {
    private String token;
    private List<String> roles;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
