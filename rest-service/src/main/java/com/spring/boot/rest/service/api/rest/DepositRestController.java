package com.spring.boot.rest.service.api.rest;

import com.google.gson.Gson;
import com.spring.boot.redelivery.starter.Context;
import com.spring.boot.redelivery.starter.DefaultAsyncService;
import com.spring.boot.rest.service.business.entity.Deposit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/deposit")
public class DepositRestController {
    private final DefaultAsyncService<String, String> restDeleteDepositAsyncService;

    @PostMapping()
    public String updateDeposit(@RequestBody Deposit deposit) {
        Context<Deposit> context = new Context<>(deposit);
        log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), this.getClass().getSimpleName(), "start process", deposit);
        Context<String> reqContext = new Context<>(context, restDeleteDepositAsyncService.getSystem(), deposit.getAccountName());
        reqContext.put("deposit", new Gson().toJson(deposit));
        restDeleteDepositAsyncService.send(reqContext);
        log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), this.getClass().getSimpleName(), "end process", "ok");
        return "ok";
    }

}
