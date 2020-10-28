package com.spring.boot.rest.service.integration.rest.axioma.deposit.delete;

import com.spring.boot.redelivery.api.Callback;
import com.spring.boot.redelivery.api.DataProvider;
import com.spring.boot.redelivery.async.kafka.starter.DefaultAsyncService;
import com.spring.boot.redelivery.async.kafka.starter.RedeliveryService;
import lombok.RequiredArgsConstructor;
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
}
