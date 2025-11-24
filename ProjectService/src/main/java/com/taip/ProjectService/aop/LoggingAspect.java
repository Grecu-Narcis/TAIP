package com.taip.ProjectService.aop;

import com.taip.ProjectService.logger.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.taip.ProjectService.business.ProjectService.*(..))")
    public void reportServiceMethods() {}

    @Before("reportServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        Logger.logInfo("Executing method: " + joinPoint.getSignature().getName());
    }

    @After("reportServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        Logger.logInfo("Completed method: " + joinPoint.getSignature().getName());
    }
}
