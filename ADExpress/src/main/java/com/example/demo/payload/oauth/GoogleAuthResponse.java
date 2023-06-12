package com.example.demo.payload.oauth;

import com.example.demo.models.jwt.JwtResponse;

public class GoogleAuthResponse {
    private String username;
    private JwtResponse jwtResponse;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public JwtResponse getJwtResponse() {
        return jwtResponse;
    }

    public void setJwtResponse(JwtResponse jwtResponse) {
        this.jwtResponse = jwtResponse;
    }
}
