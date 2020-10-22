package com.spring.boot.db.rest.service.api.rest;

import com.spring.boot.db.rest.service.business.entity.Deposit;
import com.spring.boot.redelivery.starter.Context;
import com.spring.boot.redelivery.starter.DefaultSyncService;
import com.spring.boot.redelivery.starter.NonRedeliveryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/deposit")
public class DepositRestController {
    private final DefaultSyncService<Deposit, Deposit> dbInsertDepositSyncService;
    private final DefaultSyncService<String, Deposit> dbSelectDepositSyncService;
    private final DefaultSyncService<String, String> dbDeleteDepositSyncService;

    @PostMapping()
    public Deposit createDeposit(@RequestBody Deposit deposit) throws NonRedeliveryException {
        Context<Deposit> context = new Context<>(deposit);
        log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), this.getClass().getSimpleName(), "start process", deposit);
        Deposit created = dbInsertDepositSyncService.send(new Context<>(context, dbSelectDepositSyncService.getSystem(), deposit));
        log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), this.getClass().getSimpleName(), "end process", created);
        return created;
    }

    @GetMapping("{name}")
    public Deposit getDepositByName(@PathVariable String name) throws NonRedeliveryException {
        Context<String> context = new Context<>(name);
        log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), this.getClass().getSimpleName(), "start process", name);
        Deposit deposit = dbSelectDepositSyncService.send(new Context<>(context, dbSelectDepositSyncService.getSystem(), name));
        log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), this.getClass().getSimpleName(), "end process", deposit);
        return deposit;
    }

    @DeleteMapping("{name}")
    public void deleteDepositByName(@PathVariable String name) throws NonRedeliveryException {
        Context<String> context = new Context<>(name);
        log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), this.getClass().getSimpleName(), "start process", name);
        String result = dbDeleteDepositSyncService.send(new Context<>(context, dbSelectDepositSyncService.getSystem(), name));
        log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), this.getClass().getSimpleName(), "end process", result);
    }

}
