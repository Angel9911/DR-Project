package com.example.demo.config.cache_config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.TimeUnit;

//@Configuration
public class CaffeineCacheConfig {

  //  @Bean
    public CaffeineCacheManager cacheManager(){
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("ccustomer");
        caffeineCacheManager.setCaffeine(caffeineCacheBuilder());
        return caffeineCacheManager;
    }
    public Caffeine<Object,Object> caffeineCacheBuilder(){
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500)
                .expireAfterAccess(10, TimeUnit.SECONDS)
                .weakKeys()
                .recordStats();
    }
}
