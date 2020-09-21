package com.spring.boot.kafka.consumer.service.common;

import com.spring.boot.kafka.consumer.service.business.entity.Article;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
class KafkaProducerConfig {

    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, Article> producerFactory() {
        return new DefaultKafkaProducerFactory<>(
                producerConfigs(),
                new StringSerializer(),
                new JsonSerializer<Article>().noTypeInfo());
    }

    @Bean
    public KafkaTemplate<String, Article> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
