package com.spring.boot.redelivery.async.kafka.starter;

import com.google.gson.Gson;
import com.spring.boot.redelivery.api.Context;
import com.spring.boot.redelivery.async.kafka.starter.config.KafkaDeliveryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
public class RedeliveryService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Clock clock;
    private final KafkaDeliveryProperties kafkaDeliveryProperties;

    public void doDelivery(Context<?> context, long secondToActivate) {
        Delivery delivery = createDelivery(context, secondToActivate);
        String json = new Gson().toJson(delivery);
        //todo виснет поток на 60 сек когда кафка не поднята, надо чето сделать
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaDeliveryProperties.getDeliveryTopic(), json);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void doDeadDelivery(Context<?> context) {
        Delivery delivery = createDeadDelivery(context);
        String json = new Gson().toJson(delivery);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaDeliveryProperties.getDeadDeliveryTopic(), json);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Delivery createDeadDelivery(Context<?> context) {
        context.setCount(0);
        return createDelivery(context, 0);
    }

    private Delivery createDelivery(Context<?> context, long secondToActivate) {
        Delivery delivery = new Delivery();
        delivery.setUuid(context.getUuid());
        delivery.setServiceId(context.getSystem());
        delivery.setContext(new Gson().toJson(context));
        delivery.setActivationDate(LocalDateTime.now(clock).plusSeconds(secondToActivate));
        return delivery;
    }
}
