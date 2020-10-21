package com.spring.boot.rest.service.integration.soap.artice.get;

import com.samples.soap.GetArticleRequest;
import com.samples.soap.GetArticleResponse;
import com.spring.boot.redelivery.starter.Callback;
import com.spring.boot.redelivery.starter.DataProvider;
import com.spring.boot.redelivery.starter.DefaultAsyncService;
import com.spring.boot.redelivery.starter.RedeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapGetArticleServiceConfig {
    @Autowired
    @Qualifier("soapGetArticleProvider")
    private DataProvider<GetArticleRequest, GetArticleResponse> dataProvider;
    @Autowired
    @Qualifier("soapGetArticleCallback")
    private Callback<GetArticleRequest, GetArticleResponse> callback;
    @Autowired
    private RedeliveryService redeliveryService;
    @Value("${retry.default.count}")
    private int redeliveryCount;
    @Value("${retry.activate.second}")
    private long activateSecond;
    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public DefaultAsyncService<GetArticleRequest, GetArticleResponse> soapGetArticleAsyncService() {
        return new DefaultAsyncService<>(dataProvider,
                redeliveryService,
                redeliveryCount,
                activateSecond,
                callback,
                appName + "_soapGetArticleAsyncService",
                GetArticleRequest.class);
    }
}
