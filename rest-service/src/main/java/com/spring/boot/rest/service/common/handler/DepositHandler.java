package com.spring.boot.rest.service.common.handler;

import com.google.gson.Gson;
import com.spring.boot.redelivery.starter.Context;
import com.spring.boot.redelivery.starter.DefaultAsyncService;
import com.spring.boot.redelivery.starter.log.StartProcess;
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
