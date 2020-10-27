package com.spring.boot.redelivery.service.api.schedule;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import com.spring.boot.redelivery.service.integration.db.DeliveryStorageMapper;
import com.spring.boot.redelivery.service.integration.kafka.KafkaSender;
import com.spring.boot.redelivery.service.model.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static brave.internal.codec.HexCodec.lowerHexToUnsignedLong;


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
            long id = lowerHexToUnsignedLong(traceId);
            long spanId = generateNewSpanId();
            TraceContext traceContext = TraceContext.newBuilder()
                    .traceId(id)
                    .spanId(spanId)
                    .sampled(true)
                    .build();
            Span span = tracer.toSpan(traceContext);
            try (Tracer.SpanInScope spanInScope = tracer.withSpanInScope(span.start())) {
                log.info("before run: {}", delivery);
                kafkaSender.send(delivery);
                deliveryStorageMapper.delete(delivery);
                log.info("after run: {}", delivery);
            } finally {
                span.finish();
            }
        }
    }

    private long generateNewSpanId() {
        Span newSpan = tracer.nextSpan().name(appName).start();
        return newSpan.context().spanId();
    }
}
