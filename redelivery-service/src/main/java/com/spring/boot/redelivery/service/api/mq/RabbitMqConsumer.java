package com.spring.boot.redelivery.service.api.mq;

import brave.Tracer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spring.boot.redelivery.service.integration.db.DeliveryStorageMapper;
import com.spring.boot.redelivery.service.model.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqConsumer {
    private final DeliveryStorageMapper deliveryStorageMapper;
    private final Tracer tracer;

    @RabbitListener(queues = "${redelivery.queue}")
    public void deliveryMqConsume(String json) {
        log.info("before deliveryMqConsume: {}", json);
        Delivery delivery = new Gson().fromJson(json, new TypeToken<Delivery>() {
        }.getType());
        String traceId = tracer.currentSpan().context().traceIdString();
        delivery.setTraceId(traceId);
        deliveryStorageMapper.insert(delivery);
        log.info("after deliveryMqConsume: {}", json);
    }
}
