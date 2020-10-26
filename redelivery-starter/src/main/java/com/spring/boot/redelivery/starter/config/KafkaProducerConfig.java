package com.spring.boot.redelivery.starter.config;

//@Configuration
//@EnableConfigurationProperties(RedeliveryKafkaProperties.class)
//@RequiredArgsConstructor
class KafkaProducerConfig {
//    private final RedeliveryKafkaProperties redeliveryKafkaProperties;
//
//    @Bean
//    public Map<String, Object> producerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, redeliveryKafkaProperties.getBootstrapServers());
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return props;
//    }
//
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(
//                producerConfigs(),
//                new StringSerializer(),
//                new StringSerializer());
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
}
