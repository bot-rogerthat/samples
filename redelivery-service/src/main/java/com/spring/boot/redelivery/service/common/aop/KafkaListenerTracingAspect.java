package com.spring.boot.redelivery.service.common.aop;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.contrib.kafka.TracingKafkaUtils;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@RequiredArgsConstructor
public class KafkaListenerTracingAspect {
    private final Tracer tracer;

    @Around("@annotation(org.springframework.kafka.annotation.KafkaListener)")
    public void aroundAdvice(ProceedingJoinPoint jp) throws Throwable {
        ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) jp.getArgs()[0];
        SpanContext spanContext = TracingKafkaUtils.extractSpanContext(record.headers(), tracer);
        Span span = tracer
                .buildSpan(jp.getTarget().getClass().getName())
                .asChildOf(spanContext)
                .start();
        tracer.activateSpan(span);
        jp.proceed();
        span.finish();
    }
}
