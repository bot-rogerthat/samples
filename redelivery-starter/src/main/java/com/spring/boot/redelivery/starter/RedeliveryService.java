package com.spring.boot.redelivery.starter;

import com.google.gson.Gson;
import com.spring.boot.redelivery.starter.config.RabbitMqProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.Clock;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
public class RedeliveryService {
    private final RabbitTemplate rabbitTemplate;
    private final Clock clock;
    private final RabbitMqProperties rabbitMqProperties;

    public void doDelivery(Context<?> context, long secondToActivate) {
        Delivery delivery = createDelivery(context, secondToActivate);
        String json = new Gson().toJson(delivery);
        log.info("before doDelivery: {}", json);
        rabbitTemplate.convertAndSend(rabbitMqProperties.getQueue(), json);
        log.info("after doDelivery: {}", json);
    }

    private Delivery createDelivery(Context<?> context, long secondToActivate) {
        Delivery delivery = new Delivery();
        delivery.setUuid(context.getUuid());
        //todo выпилить serviceId из context'a
        delivery.setServiceId(context.getServiceId());
        delivery.setContext(new Gson().toJson(context));
        delivery.setActivationDate(LocalDateTime.now(clock).plusSeconds(secondToActivate));
        return delivery;
    }
}
