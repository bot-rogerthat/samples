package com.spring.boot.rest.service.integration.rest.delete.deposit;

import com.spring.boot.redelivery.starter.Callback;
import com.spring.boot.redelivery.starter.DataProvider;
import com.spring.boot.redelivery.starter.DefaultAsyncService;
import com.spring.boot.redelivery.starter.RedeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RestDeleteDepositServiceConfig {
    private final DataProvider<String, String> restDeleteDepositProvider;
    private final Callback<String, String> restDeleteDepositCallback;
    @Autowired
    private RedeliveryService redeliveryService;
    @Value("${retry.default.count}")
    private int redeliveryCount;
    @Value("${retry.activate.second}")
    private long activateSecond;
    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public DefaultAsyncService<String, String> restDeleteDepositAsyncService() {
        return new DefaultAsyncService<>(restDeleteDepositProvider,
                redeliveryService,
                redeliveryCount,
                activateSecond,
                restDeleteDepositCallback,
                appName + "_" + "restDeleteDepositAsyncService",
                String.class);
    }

    @Bean
    public Queue restDeleteDepositAsyncQueue() {
        return new Queue(appName + "_" + "restDeleteDepositAsyncService", true);
    }

    @Bean
    public SimpleMessageListenerContainer restDeleteDepositAsyncContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(appName + "_" + "restDeleteDepositAsyncService");
        container.setMessageListener(restDeleteDepositAsyncService());
        return container;
    }
}
