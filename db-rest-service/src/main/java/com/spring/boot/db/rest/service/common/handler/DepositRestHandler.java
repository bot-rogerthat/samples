package com.spring.boot.db.rest.service.common.handler;

import com.spring.boot.db.rest.service.business.entity.Deposit;
import com.spring.boot.redelivery.starter.Context;
import com.spring.boot.redelivery.starter.DefaultSyncService;
import com.spring.boot.redelivery.starter.NonRedeliveryException;
import com.spring.boot.redelivery.starter.log.StartProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositRestHandler {
    private final DefaultSyncService<Deposit, Deposit> dbInsertDepositSyncService;
    private final DefaultSyncService<String, Deposit> dbSelectDepositSyncService;
    private final DefaultSyncService<String, String> dbDeleteDepositSyncService;

    @StartProcess
    public Deposit createDeposit(Context<Deposit> context) throws NonRedeliveryException {
        return dbInsertDepositSyncService.send(context);
    }

    @StartProcess
    public Deposit getDeposit(Context<String> context) throws NonRedeliveryException {
        return dbSelectDepositSyncService.send(context);
    }

    @StartProcess
    public void deleteDeposit(Context<String> context) throws NonRedeliveryException {
        dbDeleteDepositSyncService.send(context);
    }
}
