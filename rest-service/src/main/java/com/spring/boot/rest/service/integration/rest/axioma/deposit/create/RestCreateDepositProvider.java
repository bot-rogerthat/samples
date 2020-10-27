package com.spring.boot.rest.service.integration.rest.axioma.deposit.create;

import com.spring.boot.redelivery.starter.DataProvider;
import com.spring.boot.redelivery.starter.RedeliveryException;
import com.spring.boot.rest.service.business.entity.Deposit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestCreateDepositProvider implements DataProvider<Deposit, Deposit> {
    private final RestTemplate restTemplate;
    @Value("${deposit.service.url}")
    private String url;

    @Override
    public Deposit invoke(Deposit request) throws RedeliveryException {
        try {
            return restTemplate.postForObject(url, request, Deposit.class);
        } catch (Exception e) {
            throw new RedeliveryException(e);
        }
    }
}
