package com.spring.boot.redelivery.service.api.kafka;

import brave.Tracer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spring.boot.redelivery.service.integration.db.DeliveryStorageMapper;
import com.spring.boot.redelivery.service.model.Delivery;
import com.spring.boot.redelivery.starter.config.KafkaDeliveryProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaDeliveryConsumer {
    private final DeliveryStorageMapper deliveryStorageMapper;
    @Getter
    private final KafkaDeliveryProperties kafkaDeliveryProperties;
    private final Tracer tracer;

    @KafkaListener(
            topics = "#{__listener.kafkaDeliveryProperties.getDeliveryTopic()}",
            groupId = "#{__listener.kafkaDeliveryProperties.getGroupId()}")
    public void deliveryConsume(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        String message = record.value();
        log.info("before deliveryConsume: {}", message);
        Delivery delivery = new Gson().fromJson(message, new TypeToken<Delivery>() {
        }.getType());
        String traceId = tracer.currentSpan().context().traceIdString();
        delivery.setTraceId(traceId);
        deliveryStorageMapper.insert(delivery);
        acknowledgment.acknowledge();
        log.info("after deliveryConsume: {}", message);
    }
}
