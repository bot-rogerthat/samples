package com.spring.boot.redelivery.service.api.kafka;

import brave.Tracer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spring.boot.redelivery.service.integration.db.DeadDeliveryStorageMapper;
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
public class KafkaDeadDeliveryConsumer {
    private final DeadDeliveryStorageMapper deadDeliveryStorageMapper;
    @Getter
    private final KafkaDeliveryProperties kafkaDeliveryProperties;
    private final Tracer tracer;

    @KafkaListener(
            topics = "#{__listener.kafkaDeliveryProperties.getDeadDeliveryTopic()}",
            groupId = "#{__listener.kafkaDeliveryProperties.getGroupId()}")
    public void deadDeliveryConsume(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        String message = record.value();
        log.info("before deadDeliveryConsume: {}", message);
        Delivery delivery = new Gson().fromJson(message, new TypeToken<Delivery>() {
        }.getType());
        String traceId = tracer.currentSpan().context().traceIdString();
        delivery.setTraceId(traceId);
        deadDeliveryStorageMapper.insert(delivery);
        acknowledgment.acknowledge();
        log.info("after deadDeliveryConsume: {}", message);
    }
}
