package com.spring.boot.redelivery.service.integration.mq;

import com.spring.boot.redelivery.service.model.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqSender {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(Delivery delivery) {
        String serviceId = delivery.getServiceId();
        String context = delivery.getContext();
        log.info("before send: topic={}, message={}", serviceId, context);
        rabbitTemplate.convertAndSend(serviceId, context);
        log.info("before after: topic={}, message={}", serviceId, context);
    }
}
