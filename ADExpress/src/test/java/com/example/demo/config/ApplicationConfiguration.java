package com.example.demo.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ApplicationConfiguration {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
