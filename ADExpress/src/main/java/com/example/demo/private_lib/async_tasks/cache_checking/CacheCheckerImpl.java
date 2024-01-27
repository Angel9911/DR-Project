package com.example.demo.private_lib.async_tasks.cache_checking;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CacheCheckerImpl implements CacheChecker{
    private Map<String, Object> cacheValues;

    public CacheCheckerImpl() {
        this.cacheValues = new LinkedHashMap<>();
    }

    @Override
    public void addToCache(String key, Map<String, List<Object>> values) {

    }

    @Override
    public Map<String, Object> getFromCache() {
        return null;
    }
}
