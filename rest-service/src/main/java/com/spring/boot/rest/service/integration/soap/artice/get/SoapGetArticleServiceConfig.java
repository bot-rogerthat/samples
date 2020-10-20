package com.spring.boot.rest.service.integration.soap.artice.get;

import com.samples.soap.GetArticleRequest;
import com.samples.soap.GetArticleResponse;
import com.spring.boot.rest.service.redelivery.Callback;
import com.spring.boot.rest.service.redelivery.DataProvider;
import com.spring.boot.rest.service.redelivery.DefaultAsyncService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapGetArticleServiceConfig {
    @Qualifier("soapGetArticleProvider")
    private DataProvider<GetArticleRequest, GetArticleResponse> dataProvider;
    @Value("${retry.default.count}")
    private int redeliveryCount;
    @Value("${retry.activate.second}")
    private long activateSecond;
    @Qualifier("soapGetArticleCallback")
    private Callback<GetArticleRequest, GetArticleResponse> callback;
    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public DefaultAsyncService<GetArticleRequest, GetArticleResponse> soapGetArticleAsyncService() {
        return new DefaultAsyncService<>(dataProvider,
                redeliveryCount,
                activateSecond,
                callback,
                appName +  "_soapGetArticleAsyncService");
    }
}
