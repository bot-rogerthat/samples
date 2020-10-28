package com.spring.boot.db.rest.service.integration.db.select;

import com.spring.boot.db.rest.service.business.entity.Deposit;
import com.spring.boot.db.rest.service.common.mapper.DbDepositMapper;
import com.spring.boot.redelivery.api.DataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbSelectDepositProvider implements DataProvider<String, Deposit> {
    private final DbDepositMapper dbDepositMapper;

    @Override
    public Deposit invoke(String request) {
        return dbDepositMapper.selectByName(request);
    }
}
