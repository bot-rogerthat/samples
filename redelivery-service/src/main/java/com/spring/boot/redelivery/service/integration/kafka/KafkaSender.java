package com.spring.boot.redelivery.service.integration.kafka;

//
//import com.spring.boot.redelivery.service.model.Delivery;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Component;
//import org.springframework.util.concurrent.ListenableFuture;
//
//import java.util.concurrent.ExecutionException;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
public class KafkaSender {
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    public void sendMessage(Delivery delivery) {
//        String serviceId = delivery.getServiceId();
//        String context = delivery.getContext();
//        log.info("before send: topic={}, message={}", serviceId, context);
//        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(serviceId, context);
//        try {
//            future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//        log.info("before after: topic={}, message={}", serviceId, context);
//    }
}
