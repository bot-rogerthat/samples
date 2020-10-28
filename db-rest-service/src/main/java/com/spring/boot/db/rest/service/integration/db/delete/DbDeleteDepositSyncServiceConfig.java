package com.spring.boot.db.rest.service.integration.db.delete;

import com.spring.boot.redelivery.api.DataProvider;
import com.spring.boot.redelivery.sync.starter.DefaultSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DbDeleteDepositSyncServiceConfig {
    private final DataProvider<String, String> dbDeleteDepositProvider;
    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public DefaultSyncService<String, String> dbDeleteDepositSyncService() {
        return new DefaultSyncService<>(dbDeleteDepositProvider, appName + "_" + "dbDeleteDepositSyncService");
    }
}
