package com.spring.boot.rest.service.integration.rest.axioma.deposit.create;

import com.spring.boot.redelivery.api.DataProvider;
import com.spring.boot.redelivery.api.DefaultCallback;
import com.spring.boot.redelivery.async.kafka.starter.DefaultAsyncService;
import com.spring.boot.redelivery.async.kafka.starter.RedeliveryService;
import com.spring.boot.rest.service.business.entity.Deposit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RestCreateDepositServiceConfig {
    private final DataProvider<Deposit, Deposit> restCreateDepositProvider;
    @Autowired
    private RedeliveryService redeliveryService;
    @Value("${retry.default.count}")
    private int redeliveryCount;
    @Value("${retry.activate.second}")
    private long activateSecond;
    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public DefaultAsyncService<Deposit, Deposit> restCreateDepositAsyncService() {
        return new DefaultAsyncService<>(restCreateDepositProvider,
                redeliveryService,
                redeliveryCount,
                activateSecond,
                new DefaultCallback<>(),
                appName + "_" + "restCreateDepositAsyncService",
                Deposit.class);
    }
}
