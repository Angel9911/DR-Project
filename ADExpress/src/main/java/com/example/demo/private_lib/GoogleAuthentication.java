package com.example.demo.private_lib;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Roles;
import com.example.demo.models.entity.User_account;
import com.example.demo.models.jwt.JwtResponse;
import com.example.demo.payload.oauth.GoogleAuthRequest;
import com.example.demo.payload.oauth.GoogleAuthResponse;
import com.example.demo.repositories.CustomerRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GoogleAuthentication {
    Logger logger = LoggerFactory.getLogger(GoogleAuthentication.class);

    private final String clientId;

    private final CustomerRepository customerRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private GoogleAuthResponse googleAuthResponse;

    private HttpTransport httpTransport;

    private GsonFactory gsonFactory;

    public GoogleAuthentication(String clientId, CustomerRepository customerRepository, JwtTokenUtil jwtTokenUtil) {
        this.clientId = clientId;
        this.customerRepository = customerRepository;
        this.jwtTokenUtil = jwtTokenUtil;

        googleAuthResponse = new GoogleAuthResponse();

        httpTransport = new NetHttpTransport();
        gsonFactory = new GsonFactory();
    }

    public GoogleAuthResponse authenticationGoogleToken(GoogleAuthRequest token) throws GeneralSecurityException, IOException {
        logger.info("ClientID(it's located in app.properties): "+clientId);

        GoogleIdToken googleIdToken = this.getGoogleIdToken(token.getGoogleAuthToken());

        Optional<Customer> customer = this.getCustomerByGoogleIdToken(googleIdToken);

        if(customer.isPresent()){
            User_account userAccount = this.getUserAccountByCustomer(customer.get());
            if(userAccount!=null){
                return this.createGoogleAuthResponse(userAccount);
            }
        }
        return null;
    }

    private GoogleIdToken getGoogleIdToken(String authToken) throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, gsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken idToken = verifier.verify(authToken);

        if(idToken == null){
            logger.error("idToken is null. Maybe it's not correct.");
            throw new IllegalArgumentException();
        }
        return idToken;
    }

    private Optional<Customer> getCustomerByGoogleIdToken(GoogleIdToken idToken){
        GoogleIdToken.Payload payload = idToken.getPayload();

        String getEmail = payload.getEmail();

        return customerRepository.findByEmail(getEmail);
    }

    private User_account getUserAccountByCustomer(Customer customer){
        User_account user_account = customer.getAccount();
        return user_account;
    }

    private GoogleAuthResponse createGoogleAuthResponse(User_account user_account){
        JwtResponse authResponse = new JwtResponse();

        String jwttoken = jwtTokenUtil.generateJwtTokenByUsername(user_account.getUsername());

        List<Roles> rolesList = user_account.getUserRoles();

        List<String> getRoles = new ArrayList<String>();

        rolesList.forEach((user) -> getRoles.add(user.getRole_description().name()));

        authResponse.setToken(jwttoken);
        authResponse.setRoles(getRoles);

        this.googleAuthResponse.setUsername(user_account.getUsername());
        logger.info("Generated token is "+authResponse.getToken());
        this.googleAuthResponse.setJwtResponse(authResponse);

        return this.googleAuthResponse;
    }
}
