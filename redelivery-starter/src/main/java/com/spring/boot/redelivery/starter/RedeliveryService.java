package com.spring.boot.redelivery.starter;

import com.google.gson.Gson;
import com.spring.boot.redelivery.starter.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Slf4j
public class RedeliveryService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Clock clock;
    private final KafkaProperties kafkaProperties;

    public void doDelivery(Context<?> context, long secondToActivate) {
        Delivery delivery = createDelivery(context, secondToActivate);
        String json = new Gson().toJson(delivery);
        log.info("before doDelivery: {}", json);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaProperties.getTopic(), json);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
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
