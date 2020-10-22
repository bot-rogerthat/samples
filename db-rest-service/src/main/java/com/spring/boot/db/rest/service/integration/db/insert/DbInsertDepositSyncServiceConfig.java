package com.spring.boot.db.rest.service.integration.db.insert;

import com.spring.boot.db.rest.service.business.entity.Deposit;
import com.spring.boot.redelivery.starter.DataProvider;
import com.spring.boot.redelivery.starter.DefaultSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DbInsertDepositSyncServiceConfig {
    private final DataProvider<Deposit, Deposit> dbInsertDepositProvider;
    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public DefaultSyncService<Deposit, Deposit> dbInsertDepositSyncService() {
        return new DefaultSyncService<>(dbInsertDepositProvider, appName + "_" + "dbInsertDepositSyncService");
    }
}
