package com.spring.boot.rest.service.integration.soap.artice.add;

import com.samples.soap.AddArticleRequest;
import com.samples.soap.AddArticleResponse;
import com.spring.boot.rest.service.redelivery.DefaultAsyncService;
import com.spring.boot.rest.service.redelivery.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SoapAddArticleService extends DefaultAsyncService<AddArticleRequest, AddArticleResponse> {

    @Autowired
    public SoapAddArticleService(@Qualifier("soapAddArticleProvider") DataProvider<AddArticleRequest, AddArticleResponse> dataProvider,
                                 @Value("${retry.default.count}") int redeliveryCount) {
        super(dataProvider, redeliveryCount);
    }
}
