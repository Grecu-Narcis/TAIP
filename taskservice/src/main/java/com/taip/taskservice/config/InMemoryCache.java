package com.taip.taskservice.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryCache {
    private final Map<String, Object> cache = new HashMap<>();

    public Object get(String key) {
        return cache.get(key);
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
