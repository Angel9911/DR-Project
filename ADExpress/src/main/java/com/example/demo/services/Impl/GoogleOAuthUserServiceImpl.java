package com.example.demo.services.Impl;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Roles;
import com.example.demo.models.entity.User_account;
import com.example.demo.models.jwt.JwtResponse;
import com.example.demo.payload.oauth.GoogleAuthRequest;
import com.example.demo.payload.oauth.GoogleAuthResponse;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.services.GoogleOAuthUserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleOAuthUserServiceImpl implements GoogleOAuthUserService {
    Logger logger = LoggerFactory.getLogger(GoogleOAuthUserServiceImpl.class);

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    private final CustomerRepository customerRepository;

    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    public GoogleOAuthUserServiceImpl(CustomerRepository customerRepository, JwtTokenUtil jwtTokenUtil){

        this.customerRepository = customerRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @Override
    public GoogleAuthResponse verifyToken(GoogleAuthRequest token) throws GeneralSecurityException, IOException {
        GoogleAuthResponse googleAuthResponse = new GoogleAuthResponse();

        HttpTransport httpTransport = new NetHttpTransport();
        GsonFactory gsonFactory =  new GsonFactory();
        logger.info("ClientID(it's located in app.properties): "+clientId);
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, gsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();


        GoogleIdToken idToken = verifier.verify(token.getGoogleAuthToken());
        JwtResponse authResponse = new JwtResponse();
        if(idToken == null){
            logger.error("idToken is null. Maybe it's not correct.");
            throw new IllegalArgumentException();
        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        String getEmail = payload.getEmail();
        Customer customer = customerRepository.findByEmail(getEmail);
        System.out.println(customer);
        if(customer != null){
            User_account user_account = customer.getUser_account();
            if(user_account!=null){

                String jwttoken = jwtTokenUtil.generateJwtTokenByUsername(user_account.getUsername());

                List<Roles> rolesList = user_account.getUserRoles();
                List<String> getRoles = new ArrayList<String>();
                rolesList.forEach((user) -> getRoles.add(user.getRole_description().name()));
                authResponse.setToken(jwttoken);
                authResponse.setRoles(getRoles);
                googleAuthResponse.setUsername(user_account.getUsername());
                googleAuthResponse.setJwtResponse(authResponse);
                logger.info("Generated token is "+authResponse.getToken());
            }
        }
        return googleAuthResponse;
    }

}
