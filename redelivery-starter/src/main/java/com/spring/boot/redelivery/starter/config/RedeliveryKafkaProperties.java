package com.spring.boot.redelivery.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "redelivery")
public class RedeliveryKafkaProperties {
    private String bootstrapServers;
    private String topic;
    private String groupId;
    private Long backOffPeriodMs;
    private Integer maxAttempts;
}
