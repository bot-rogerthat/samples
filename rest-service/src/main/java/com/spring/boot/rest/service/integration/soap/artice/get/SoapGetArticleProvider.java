package com.spring.boot.rest.service.integration.soap.artice.get;

import com.samples.soap.GetArticleRequest;
import com.samples.soap.GetArticleResponse;
import com.spring.boot.redelivery.api.DataProvider;
import com.spring.boot.redelivery.api.RedeliveryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
@RequiredArgsConstructor
public class SoapGetArticleProvider implements DataProvider<GetArticleRequest, GetArticleResponse> {
    private final WebServiceTemplate webServiceTemplate;

    @Override
    public GetArticleResponse invoke(GetArticleRequest request) throws RedeliveryException {
        try {
            return (GetArticleResponse) webServiceTemplate.marshalSendAndReceive(request);
        } catch (Exception e) {
            throw new RedeliveryException(e);
        }
    }
}
