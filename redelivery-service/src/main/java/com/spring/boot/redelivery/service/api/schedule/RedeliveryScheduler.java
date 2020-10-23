package com.spring.boot.redelivery.service.api.schedule;

import com.spring.boot.redelivery.service.integration.db.DeliveryStorageMapper;
import com.spring.boot.redelivery.service.integration.kafka.KafkaSender;
import com.spring.boot.redelivery.service.model.Delivery;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class RedeliveryScheduler {
    private final DeliveryStorageMapper deliveryStorageMapper;
    private final KafkaSender kafkaSender;
    private final Clock clock;
    private final Tracer tracer;
    @Value("${delivery.limit.rows}")
    private long limitRows;
    @Value("${spring.application.name}")
    private String appName;

    @Scheduled(cron = "${cron.expression}")
    @Transactional
    public void run() {
        List<Delivery> deliveries = deliveryStorageMapper.select(LocalDateTime.now(clock), limitRows);
        for (Delivery delivery : deliveries) {
            String traceId = delivery.getTraceId();
            long longTraceId = Long.parseUnsignedLong(traceId, 16);
            Span span = tracer.buildSpan(appName).start();
            SpanContext context = span.context();

            try {
                FieldUtils.writeField(context, "traceIdLow", longTraceId, true);
                FieldUtils.writeField(context, "traceIdAsString", traceId, true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            try (Scope scope = tracer.scopeManager().activate(span)) {
                log.info("before run: {}", delivery);
                kafkaSender.sendMessage(delivery);
                deliveryStorageMapper.delete(delivery);
                log.info("after run: {}", delivery);
            } finally {
                span.finish();
            }
        }
    }
}
