package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.example.demo.repositories",entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@EntityScan(basePackages = { "com.example.demo.models" })
@ComponentScan(basePackages = {"com.example.demo.config", "com.example.demo.services.Impl", "com.example.demo.controllers"})
@ComponentScan(basePackages = "com.example.demo.services")
//@ComponentScan(basePackages = "com.example.demo.repository")
public class AdExpressApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdExpressApplication.class, args);
    }

}
