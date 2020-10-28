package com.spring.boot.redelivery.starter.config;

import com.spring.boot.redelivery.starter.Context;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import static com.spring.boot.redelivery.starter.log.LogUtils.logEnd;
import static com.spring.boot.redelivery.starter.log.LogUtils.logStart;

@Aspect
@Configuration
public class StartProcessAspect {

    @Around("@annotation(com.spring.boot.redelivery.starter.log.StartProcess)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Context<?> context = (Context<?>) args[0];
        String methodName = joinPoint.getSignature().getName();
        long startTime = logStart(methodName, context);
        Object response = joinPoint.proceed(args);
        logEnd(startTime, methodName, context, response);
        return response;
    }
}
