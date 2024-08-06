package com.example.demo.config;

import com.example.demo.private_lib.GoogleAuthentication;
import com.example.demo.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleAuthConfig {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Bean
    public GoogleAuthentication getGoogleAuthInstance(CustomerRepository customerRepository, JwtTokenUtil jwtTokenUtil){
        return new GoogleAuthentication(clientId,customerRepository, jwtTokenUtil);
    }

}
