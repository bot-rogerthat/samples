package com.spring.boot.db.rest.service.integration.db.select;

import com.spring.boot.db.rest.service.business.entity.Deposit;
import com.spring.boot.redelivery.api.DataProvider;
import com.spring.boot.redelivery.sync.starter.DefaultSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DbSelectDepositSyncServiceConfig {
    private final DataProvider<String, Deposit> dbSelectDepositProvider;
    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public DefaultSyncService<String, Deposit> dbSelectDepositSyncService() {
        return new DefaultSyncService<>(dbSelectDepositProvider, appName + "_" + "dbSelectDepositSyncService");
    }
}
