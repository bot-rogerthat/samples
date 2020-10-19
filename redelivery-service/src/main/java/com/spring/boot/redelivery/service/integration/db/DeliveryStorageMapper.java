package com.spring.boot.redelivery.service.integration.db;

import com.spring.boot.redelivery.service.common.entity.Delivery;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;


@Mapper
public interface DeliveryStorageMapper {

    @Insert("INSERT INTO prod.delivery(uuid, service_id, context, activation_date) " +
            "VALUES (#{uuid}," +
            " #{serviceId}," +
            " #{context})," +
            " #{activationDate}")
    void insert(Delivery delivery);

    @Select("SELECT uuid, service_id, context, activation_date " +
            "FROM prod.delivery " +
            "WHERE #{now} > activation_date " +
            "LIMIT #{limitRows} FOR UPDATE SKIP LOCKED")
    @Results({
            @Result(property = "uuid", column = "uuid"),
            @Result(property = "serviceId", column = "service_id"),
            @Result(property = "context", column = "context"),
            @Result(property = "activationDate", column = "activation_date"),
            @Result(property = "processed", column = "processed")
    })
    List<Delivery> select(LocalDateTime now, long limitRows);

    @Delete("DELETE FROM prod.delivery " +
            "WHERE uuid = #{uuid}")
    void delete(Delivery delivery);
}
