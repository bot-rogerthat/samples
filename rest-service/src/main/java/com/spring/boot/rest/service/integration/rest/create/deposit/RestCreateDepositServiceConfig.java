package com.spring.boot.rest.service.integration.rest.create.deposit;

import com.spring.boot.redelivery.starter.Callback;
import com.spring.boot.redelivery.starter.DataProvider;
import com.spring.boot.redelivery.starter.DefaultAsyncService;
import com.spring.boot.redelivery.starter.RedeliveryService;
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
    private final Callback<Deposit, Deposit> restCreateDepositCallback;
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
                restCreateDepositCallback,
                appName + "_" + "restCreateDepositAsyncService",
                Deposit.class);
    }
}
