package com.spring.boot.kafka.consumer.service.common;

import com.spring.boot.kafka.consumer.service.business.entity.Article;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL_IMMEDIATE;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    private final DlqErrorHandler dlqErrorHandler;
    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;
    @Value("${kafka.group.id}")
    private String groupId;
    @Value("${kafka.backoff.period.ms}")
    private Long backOffPeriodMs;
    @Value("${kafka.max.attempts}")
    private Integer maxAttempts;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Article> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setRetryTemplate(kafkaRetry());
        factory.setErrorHandler(dlqErrorHandler);
        factory.getContainerProperties().setAckMode(MANUAL_IMMEDIATE);
        return factory;
    }

    @Bean
    public RetryTemplate kafkaRetry() {
        RetryTemplate retryTemplate = new RetryTemplate();
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(backOffPeriodMs);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(maxAttempts);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        return retryTemplate;
    }

    @Bean
    public ConsumerFactory<String, Article> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(Article.class));
    }
}
