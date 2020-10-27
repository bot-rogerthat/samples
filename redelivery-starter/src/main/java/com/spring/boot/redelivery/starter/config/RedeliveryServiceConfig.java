package com.spring.boot.redelivery.starter.config;

import com.spring.boot.redelivery.starter.RedeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Clock;

@Configuration
@RequiredArgsConstructor
public class RedeliveryServiceConfig {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Clock clock;
    private final KafkaProperties kafkaProperties;

    @Bean
    public RedeliveryService redeliveryService() {
        return new RedeliveryService(kafkaTemplate, clock, kafkaProperties);
    }
}
