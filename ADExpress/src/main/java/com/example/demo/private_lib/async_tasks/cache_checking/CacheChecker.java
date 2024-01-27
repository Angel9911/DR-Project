package com.example.demo.private_lib.async_tasks.cache_checking;

import java.util.List;
import java.util.Map;

public interface CacheChecker {
    void addToCache(String key, Map<String, List<Object>> values);
    Map<String,Object> getFromCache();
}
