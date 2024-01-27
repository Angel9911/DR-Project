package com.example.demo.private_lib.cache_checking;

import java.util.Map;

public interface CacheChecker {
    Map<Object, Object> getFromCache(String cacheName);
}
