package com.spring.boot.kafka.consumer.service.common;


import com.spring.boot.kafka.consumer.service.business.entity.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerAwareErrorHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DlqErrorHandler implements ConsumerAwareErrorHandler {
    private final KafkaTemplate<String, Article> kafkaTemplate;
    @Value("${kafka.dql.topic}")
    private String topic;

    public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
        Article article = (Article) data.value();
        log.info("before dlq send: topic={}, message={}", topic, article);
        kafkaTemplate.send(topic, article);
        log.info("after dlq send: topic={}, message={}", topic, article);
        consumer.commitSync();
    }
}
