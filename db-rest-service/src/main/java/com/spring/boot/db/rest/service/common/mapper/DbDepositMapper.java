package com.spring.boot.db.rest.service.common.mapper;

import com.spring.boot.db.rest.service.business.entity.Deposit;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DbDepositMapper {

    @Select("213INSERT INTO deposit_schema.deposit (account_name, balance, expiration_date) " +
            "VALUES (#{accountName}, #{balance}, #{expirationDate}) " +
            "RETURNING *")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "accountName", column = "account_name"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "expirationDate", column = "expiration_date")
    })
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    Deposit insert(Deposit deposit);

    @Select("SELECT id, account_name, balance, expiration_date " +
            "FROM deposit_schema.deposit " +
            "WHERE account_name = #{name} " +
            "LIMIT 1")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "accountName", column = "account_name"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "expirationDate", column = "expiration_date")
    })
    Deposit selectByName(String name);

    @Delete("DELETE FROM deposit_schema.deposit " +
            "WHERE account_name = #{name}")
    void deleteByName(String name);
}
