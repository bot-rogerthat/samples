package com.spring.boot.rest.service.integration.soap.artice.get;


import com.samples.soap.GetArticleRequest;
import com.samples.soap.GetArticleResponse;
import com.spring.boot.redelivery.api.Context;
import com.spring.boot.redelivery.api.RedeliveryException;
import com.spring.boot.redelivery.api.SyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SoapGetArticleSyncService implements SyncService<GetArticleRequest, GetArticleResponse> {
    private final SoapGetArticleProvider soapGetArticleProvider;

    @Override
    public GetArticleResponse send(Context<GetArticleRequest> context) throws RedeliveryException {
        return soapGetArticleProvider.invoke(context.getRequest());
    }
}
