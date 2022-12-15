package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.net.URISyntaxException;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.demo.repository",entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@EntityScan(basePackages = { "com.example.demo.model" })
@ComponentScan(basePackages = "com.example.demo.services.Impl")
@ComponentScan(basePackages = {"com.example.demo.config", "com.example.demo.services","com.example.demo.controller"})
//@ComponentScan(basePackages = "com.example.demo.services")
//@ComponentScan(basePackages = "com.example.demo.repository")
public class AdExpressApplication {

    /* @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws URISyntaxException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        //entityManagerFactoryBean.setDataSource("");
        //entityManagerFactoryBean.setPackagesToScan(package_to_scan);
        //additional config of factory

        return entityManagerFactoryBean;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) throws URISyntaxException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    } */

    public static void main(String[] args) {
        SpringApplication.run(AdExpressApplication.class, args);
    }

}
