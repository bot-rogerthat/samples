package com.spring.boot.redelivery.starter.config;

public interface KafkaProperties {

    String getBootstrapServers();

    default String getDeliveryTopic() {
        return "redelivery";
    }

    default String getDeadDeliveryTopic() {
        return "dead-redelivery";
    }

    default String getGroupId() {
        return "redelivery-group-id";
    }

    default Long getBackOffPeriodMs() {
        return 5_000L;
    }

    default Integer getMaxAttempts() {
        return 3;
    }
}
