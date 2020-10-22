package com.spring.boot.db.rest.service.integration.db.delete;

import com.spring.boot.db.rest.service.common.mapper.DbDepositMapper;
import com.spring.boot.redelivery.starter.DataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbDeleteDepositProvider implements DataProvider<String, String> {
    private final DbDepositMapper dbDepositMapper;

    @Override
    public String invoke(String request) {
        dbDepositMapper.deleteByName(request);
        return "ok";
    }
}
