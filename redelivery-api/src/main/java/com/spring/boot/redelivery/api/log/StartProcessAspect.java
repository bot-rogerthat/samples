package com.spring.boot.redelivery.api.log;

import com.spring.boot.redelivery.api.Context;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.spring.boot.redelivery.api.log.LogUtils.logEnd;
import static com.spring.boot.redelivery.api.log.LogUtils.logStart;


@Aspect
@Component
public class StartProcessAspect {

    @Around("@annotation(com.spring.boot.redelivery.api.log.StartProcess)")
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
