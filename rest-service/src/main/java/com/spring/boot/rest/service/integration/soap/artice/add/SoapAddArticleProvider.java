package com.spring.boot.rest.service.integration.soap.artice.add;

import com.samples.soap.AddArticleRequest;
import com.samples.soap.AddArticleResponse;
import com.spring.boot.redelivery.api.DataProvider;
import com.spring.boot.redelivery.api.RedeliveryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
@RequiredArgsConstructor
public class SoapAddArticleProvider implements DataProvider<AddArticleRequest, AddArticleResponse> {
    private final WebServiceTemplate webServiceTemplate;

    @Override
    public AddArticleResponse invoke(AddArticleRequest request) throws RedeliveryException {
        try {
            return (AddArticleResponse) webServiceTemplate.marshalSendAndReceive(request);
        } catch (Exception e) {
            throw new RedeliveryException(e);
        }
    }
}
