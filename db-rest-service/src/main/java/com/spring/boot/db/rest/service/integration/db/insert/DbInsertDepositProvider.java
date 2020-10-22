package com.spring.boot.db.rest.service.integration.db.insert;

import com.spring.boot.db.rest.service.business.entity.Deposit;
import com.spring.boot.db.rest.service.common.mapper.DbDepositMapper;
import com.spring.boot.redelivery.starter.DataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbInsertDepositProvider implements DataProvider<Deposit, Deposit> {
    private final DbDepositMapper dbDepositMapper;

    @Override
    public Deposit invoke(Deposit request) {
        return dbDepositMapper.insert(request);
    }
}
