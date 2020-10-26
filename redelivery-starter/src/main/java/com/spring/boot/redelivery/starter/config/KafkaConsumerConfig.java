package com.spring.boot.redelivery.starter.config;

//@Configuration
//@EnableConfigurationProperties(RedeliveryKafkaProperties.class)
//@RequiredArgsConstructor
public class KafkaConsumerConfig {
//    private final RedeliveryKafkaProperties redeliveryKafkaProperties;
//
//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, redeliveryKafkaProperties.getBootstrapServers());
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, redeliveryKafkaProperties.getGroupId());
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
//        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
//        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 4 * 1000);
//        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 12 * 1000);
//        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 5 * 60 * 1000);
//        return props;
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setRetryTemplate(kafkaRetry());
//        factory.getContainerProperties().setAckMode(MANUAL_IMMEDIATE);
//        return factory;
//    }
//
//    @Bean
//    public RetryTemplate kafkaRetry() {
//        RetryTemplate retryTemplate = new RetryTemplate();
//        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
//        fixedBackOffPolicy.setBackOffPeriod(redeliveryKafkaProperties.getBackOffPeriodMs());
//        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
//        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
//        simpleRetryPolicy.setMaxAttempts(redeliveryKafkaProperties.getMaxAttempts());
//        retryTemplate.setRetryPolicy(simpleRetryPolicy);
//        return retryTemplate;
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(
//                consumerConfigs(),
//                new StringDeserializer(),
//                new StringDeserializer());
//    }
}
