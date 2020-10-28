package com.spring.boot.rest.service.integration.rest.axioma.deposit.delete;

import com.google.gson.Gson;
import com.spring.boot.redelivery.api.Context;
import com.spring.boot.redelivery.api.DefaultCallback;
import com.spring.boot.redelivery.async.kafka.starter.DefaultAsyncService;
import com.spring.boot.rest.service.business.entity.Deposit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestDeleteDepositCallback extends DefaultCallback<String, String> {
    private final DefaultAsyncService<Deposit, Deposit> restCreateDepositAsyncService;

    @Override
    public void onSuccess(String response, Context<String> context) {
        Deposit deposit = new Gson().fromJson(context.get("deposit"), Deposit.class);
        restCreateDepositAsyncService.send(new Context<>(context, deposit));
    }
}
