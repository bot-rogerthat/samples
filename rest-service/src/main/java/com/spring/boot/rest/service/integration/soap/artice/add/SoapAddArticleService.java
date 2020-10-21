package com.spring.boot.rest.service.integration.soap.artice.add;

import com.samples.soap.AddArticleRequest;
import com.samples.soap.AddArticleResponse;
import com.spring.boot.redelivery.starter.Context;
import com.spring.boot.redelivery.starter.NonRedeliveryException;
import com.spring.boot.redelivery.starter.RedeliveryException;
import com.spring.boot.redelivery.starter.SyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SoapAddArticleService implements SyncService<AddArticleRequest, AddArticleResponse> {
    private final SoapAddArticleProvider soapAddArticleProvider;

    @Override
    public AddArticleResponse send(Context<AddArticleRequest> context) throws RedeliveryException, NonRedeliveryException {
        return soapAddArticleProvider.invoke(context.getRequest());
    }

}
