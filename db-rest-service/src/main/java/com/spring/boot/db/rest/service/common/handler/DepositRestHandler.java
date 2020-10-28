package com.spring.boot.db.rest.service.common.handler;

import com.spring.boot.db.rest.service.business.entity.Deposit;
import com.spring.boot.redelivery.api.Context;
import com.spring.boot.redelivery.api.NonRedeliveryException;
import com.spring.boot.redelivery.api.log.StartProcess;
import com.spring.boot.redelivery.sync.starter.DefaultSyncService;
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
