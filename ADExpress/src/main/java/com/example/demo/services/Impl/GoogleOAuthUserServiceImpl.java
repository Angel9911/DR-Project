package com.example.demo.services.Impl;

import com.example.demo.config.JwtTokenUtil;

import com.example.demo.payload.oauth.GoogleAuthRequest;
import com.example.demo.payload.oauth.GoogleAuthResponse;
import com.example.demo.private_lib.GoogleAuthentication;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.services.GoogleOAuthUserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;


@Service
public class GoogleOAuthUserServiceImpl implements GoogleOAuthUserService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    private final GoogleAuthentication googleAuthentication;

    public GoogleOAuthUserServiceImpl(CustomerRepository customerRepository, JwtTokenUtil jwtTokenUtil){

        this.googleAuthentication = new GoogleAuthentication(clientId,customerRepository,jwtTokenUtil);
    }
    @Override
    public GoogleAuthResponse verifyToken(GoogleAuthRequest token) throws GeneralSecurityException, IOException {

        return this.googleAuthentication.authenticationGoogleToken(token);
    }

}
