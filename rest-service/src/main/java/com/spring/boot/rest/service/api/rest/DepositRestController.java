package com.spring.boot.rest.service.api.rest;

import com.spring.boot.redelivery.starter.Context;
import com.spring.boot.rest.service.business.entity.Deposit;
import com.spring.boot.rest.service.common.handler.DepositHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/deposit")
public class DepositRestController {
    private final DepositHandler depositHandler;
    @Value("${spring.application.name}")
    private String appName;

    @PostMapping()
    public String updateDeposit(@RequestBody Deposit deposit) {
        Context<Deposit> context = new Context<>(appName, deposit);
        depositHandler.updateDeposit(context);
        return "ok";
    }

}
