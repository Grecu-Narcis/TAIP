package com.taip.taskservice.aop;

import com.taip.taskservice.config.InMemoryCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CachingAspect {
    private final InMemoryCache cache;

    @Autowired
    public CachingAspect(InMemoryCache cache) {
        this.cache = cache;
    }

    @Around("@annotation(com.taip.taskservice.config.Cacheable)")
    public Object cacheMethod(ProceedingJoinPoint jointPoint) throws Throwable {
        String key = (String) jointPoint.getArgs()[0];

        Object cachedValue = cache.get(key);

        if (cachedValue != null) {
            System.out.println("Cache Hit for task with id: " + key);
            return cachedValue;
        }

        Object result = jointPoint.proceed();

        cache.put(key, result);
        System.out.println(cache);
        return result;
    }

    @Around("@annotation(com.taip.taskservice.config.CacheEvict)")
    public Object evictCache(ProceedingJoinPoint jointPoint) throws Throwable {
        String key = (String) jointPoint.getArgs()[0];

        cache.evict(key);

        System.out.println("Cache evicted for task with id: " + key);

        return jointPoint.proceed();
    }
}
