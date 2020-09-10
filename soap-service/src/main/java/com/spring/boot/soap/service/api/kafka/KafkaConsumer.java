package com.spring.boot.soap.service.api.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {
    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group.id}")
    public void listener(String data) {
        log.info(data);
    }
}
