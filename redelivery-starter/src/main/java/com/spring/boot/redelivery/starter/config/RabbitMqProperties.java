package com.spring.boot.redelivery.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "redelivery")
public class RabbitMqProperties {
    private String queue;
}
