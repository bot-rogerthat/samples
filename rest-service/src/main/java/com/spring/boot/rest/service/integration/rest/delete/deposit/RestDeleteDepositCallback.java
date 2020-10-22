package com.spring.boot.rest.service.integration.rest.delete.deposit;

import com.google.gson.Gson;
import com.spring.boot.redelivery.starter.Callback;
import com.spring.boot.redelivery.starter.Context;
import com.spring.boot.redelivery.starter.DefaultAsyncService;
import com.spring.boot.rest.service.business.entity.Deposit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestDeleteDepositCallback implements Callback<String, String> {
    private final DefaultAsyncService<Deposit, Deposit> restCreateDepositAsyncService;

    @Override
    public void onSuccess(String response, Context<String> context) {
        Deposit deposit = new Gson().fromJson(context.get("deposit"), Deposit.class);
        restCreateDepositAsyncService.send(new Context<>(context, restCreateDepositAsyncService.getSystem(), deposit));
    }

    @Override
    public void onFail(Context<String> context) {

    }

}
