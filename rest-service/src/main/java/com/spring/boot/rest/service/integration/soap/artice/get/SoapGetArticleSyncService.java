package com.spring.boot.rest.service.integration.soap.artice.get;


import com.samples.soap.GetArticleRequest;
import com.samples.soap.GetArticleResponse;
import com.spring.boot.redelivery.starter.Context;
import com.spring.boot.redelivery.starter.RedeliveryException;
import com.spring.boot.redelivery.starter.SyncService;
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
