package com.spring.boot.redelivery.starter.config;

import com.spring.boot.redelivery.starter.RedeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RabbitMqProperties.class)
public class RedeliveryServiceConfig {
    private final RabbitTemplate rabbitTemplate;
    private final Clock clock;
    private final RabbitMqProperties rabbitMqProperties;

    @Bean
    public RedeliveryService redeliveryService() {
        return new RedeliveryService(rabbitTemplate, clock, rabbitMqProperties);
    }
}
