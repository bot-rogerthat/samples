package com.spring.boot.redelivery.service.api.kafka;

import brave.Tracer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spring.boot.redelivery.service.integration.db.DeliveryStorageMapper;
import com.spring.boot.redelivery.service.model.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final DeliveryStorageMapper deliveryStorageMapper;
    private final Tracer tracer;

    @KafkaListener(topics = "${kafka.delivery.topic}", groupId = "${kafka.group.id}")
    public void deliveryConsume(String json, Acknowledgment acknowledgment) {
        log.info("before deliveryConsume: {}", json);
        Delivery delivery = new Gson().fromJson(json, new TypeToken<Delivery>() {
        }.getType());
        String traceId = tracer.currentSpan().context().traceIdString();
        delivery.setTraceId(traceId);
        deliveryStorageMapper.insert(delivery);
        acknowledgment.acknowledge();
        log.info("after deliveryConsume: {}", json);
    }
}
