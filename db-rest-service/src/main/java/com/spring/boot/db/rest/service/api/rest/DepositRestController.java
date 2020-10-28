package com.spring.boot.db.rest.service.api.rest;

import com.spring.boot.db.rest.service.business.entity.Deposit;
import com.spring.boot.db.rest.service.common.handler.DepositRestHandler;
import com.spring.boot.redelivery.api.Context;
import com.spring.boot.redelivery.api.NonRedeliveryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/deposit")
public class DepositRestController {
    private final DepositRestHandler depositRestHandler;
    @Value("${spring.application.name}")
    private String appName;

    @PostMapping()
    public Deposit createDeposit(@RequestBody Deposit deposit) throws NonRedeliveryException {
        Context<Deposit> context = new Context<>(appName, deposit);
        return depositRestHandler.createDeposit(context);
    }

    @GetMapping("{name}")
    public Deposit getDepositByName(@PathVariable String name) throws NonRedeliveryException {
        Context<String> context = new Context<>(appName, name);
        return depositRestHandler.getDeposit(context);
    }

    @DeleteMapping("{name}")
    public void deleteDepositByName(@PathVariable String name) throws NonRedeliveryException {
        Context<String> context = new Context<>(appName, name);
        depositRestHandler.deleteDeposit(context);
    }
}
