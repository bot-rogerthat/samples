package com.spring.boot.rest.service.integration.rest.axioma.deposit.delete;

import com.spring.boot.redelivery.api.DataProvider;
import com.spring.boot.redelivery.api.RedeliveryException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestDeleteDepositProvider implements DataProvider<String, String> {
    private final RestTemplate restTemplate;
    @Value("${deposit.service.url}")
    private String url;

    @Override
    public String invoke(String request) throws RedeliveryException {
        try {
            restTemplate.delete(url + "/" + request);
            return "ok";
        } catch (Exception e) {
            throw new RedeliveryException(e);
        }
    }
}
