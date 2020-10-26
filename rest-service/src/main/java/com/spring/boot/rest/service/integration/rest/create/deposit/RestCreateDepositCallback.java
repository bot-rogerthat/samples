package com.spring.boot.rest.service.integration.rest.create.deposit;

import com.spring.boot.redelivery.starter.Callback;
import com.spring.boot.rest.service.business.entity.Deposit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestCreateDepositCallback implements Callback<Deposit, Deposit> {

}
