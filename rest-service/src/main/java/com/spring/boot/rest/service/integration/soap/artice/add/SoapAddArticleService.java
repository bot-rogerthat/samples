package com.spring.boot.rest.service.integration.soap.artice.add;

import com.samples.soap.AddArticleRequest;
import com.samples.soap.AddArticleResponse;
import com.spring.boot.redelivery.api.Context;
import com.spring.boot.redelivery.api.NonRedeliveryException;
import com.spring.boot.redelivery.api.RedeliveryException;
import com.spring.boot.redelivery.api.SyncService;
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
