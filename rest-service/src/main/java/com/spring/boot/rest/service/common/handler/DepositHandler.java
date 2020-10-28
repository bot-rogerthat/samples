package com.spring.boot.rest.service.common.handler;

import com.google.gson.Gson;
import com.spring.boot.redelivery.api.Context;
import com.spring.boot.redelivery.api.log.StartProcess;
import com.spring.boot.redelivery.async.kafka.starter.DefaultAsyncService;
import com.spring.boot.rest.service.business.entity.Deposit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositHandler {
    private final DefaultAsyncService<String, String> restDeleteDepositAsyncService;

    @StartProcess
    public void updateDeposit(Context<Deposit> context) {
        Deposit deposit = context.getRequest();
        Context<String> newContext = new Context<>(context, deposit.getAccountName());
        newContext.put("deposit", new Gson().toJson(deposit));
        restDeleteDepositAsyncService.send(newContext);
    }
}
