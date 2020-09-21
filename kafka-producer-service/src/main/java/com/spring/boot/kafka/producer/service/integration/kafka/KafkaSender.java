package com.spring.boot.kafka.producer.service.integration.kafka;

import com.spring.boot.kafka.producer.service.business.entity.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaSender {
    private final KafkaTemplate<String, Article> kafkaTemplate;
    @Value("${kafka.topic}")
    private String topic;

    public void sendMessage(Article article) {
        log.info("before send: topic={}, message={}", topic, article);
        ListenableFuture<SendResult<String, Article>> future = kafkaTemplate.send(topic, article);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Article> result) {
                log.info("after send: message={}, offset={}", article, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("error send: message={}, error={}", article, ex.getMessage());
            }
        });
    }
}
