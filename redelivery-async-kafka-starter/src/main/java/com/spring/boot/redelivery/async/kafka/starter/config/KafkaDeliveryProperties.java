package com.spring.boot.redelivery.async.kafka.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "redelivery")
public class KafkaDeliveryProperties implements KafkaProperties {
    private String bootstrapServers;
}
