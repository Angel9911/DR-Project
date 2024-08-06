package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableJpaRepositories(basePackages = "com.example.demo.repositories",entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@EntityScan(basePackages = { "com.example.demo.models" })
@ComponentScan(basePackages = {"com.example.demo.config", "com.example.demo.services.Impl", "com.example.demo.controllers", "com.example.demo.private_lib.jobs", "com.example.demo.private_lib.payment.providers","com.example.demo.private_lib.chatbot.google", "com.example.demo.private_lib.cache_checking","com.example.demo.private_lib.async_tasks.verification_address"})
@ComponentScan(basePackages = {"com.example.demo.services", "com.example.demo.config.jobs","com.example.demo.config.async","com.example.demo.config.cache_config"})
//@ComponentScan(basePackages = "com.example.demo.repository")
public class AdExpressApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdExpressApplication.class, args);
    }
   /*  execute cron when run the server.
    public EmailCronJob emailCronJob(EmailServiceImpl emailService){
        return new EmailCronJob(emailService, customerService);
    } */

}
