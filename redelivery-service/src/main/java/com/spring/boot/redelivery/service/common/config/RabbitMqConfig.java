package com.spring.boot.redelivery.service.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${redelivery.queue}")
    private String redeliveryQueue;

    @Bean
    public Queue redeliveryQueue() {
        return new Queue(redeliveryQueue, true);
    }
}
