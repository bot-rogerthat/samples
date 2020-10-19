package com.spring.boot.redelivery.service.api.kafka;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spring.boot.redelivery.service.common.entity.Delivery;
import com.spring.boot.redelivery.service.integration.db.DeliveryStorageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final DeliveryStorageMapper deliveryStorageMapper;

    @KafkaListener(topics = "${kafka.delivery.topic}", groupId = "${kafka.group.id}")
    public void deliveryConsume(String json) {
        Delivery delivery = new Gson().fromJson(json, new TypeToken<Delivery>() {
        }.getType());
        deliveryStorageMapper.insert(delivery);
    }
}
