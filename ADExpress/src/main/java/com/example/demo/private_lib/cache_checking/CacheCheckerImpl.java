package com.example.demo.private_lib.cache_checking;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CacheCheckerImpl implements CacheChecker{
    private final Map<Object, Object> cacheValues;
    private final CacheManager cacheManager;


    @Autowired
    public CacheCheckerImpl(CacheManager cacheManager) {
        this.cacheValues = new LinkedHashMap<Object,Object>();

        this.cacheManager = cacheManager;
    }

    @Override
    public Map<Object, Object> getFromCache(String cacheName) {

        CaffeineCache caffeineCache = (CaffeineCache)cacheManager .getCache(cacheName);

        Cache<Object,Object> nativeCache;

        if(caffeineCache != null){

            nativeCache = caffeineCache.getNativeCache();

            for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {
                this.cacheValues.put(entry.getKey(),entry.getValue());
                System.out.println("Key = " + entry.getKey());
                System.out.println("Value = " + entry.getValue());
            }
        }
        return cacheValues;
    }
}
