package com.spring.boot.redelivery.service.integration.db;

import com.spring.boot.redelivery.service.model.Delivery;
import org.apache.ibatis.annotations.*;


@Mapper
public interface DeadDeliveryStorageMapper {

    @Insert("INSERT INTO redelivery.dead_delivery(uuid, service_id, context, activation_date, trace_id) " +
            "VALUES (#{uuid}," +
            " #{serviceId}, " +
            " #{context}::jsonb, " +
            " #{activationDate}," +
            " #{traceId})")
    void insert(Delivery delivery);

    @Select("SELECT uuid, service_id, context, activation_date, trace_id " +
            "FROM redelivery.dead_delivery " +
            "WHERE uuid = #{uuid} " +
            "FOR UPDATE SKIP LOCKED")
    @Results({
            @Result(property = "uuid", column = "uuid"),
            @Result(property = "serviceId", column = "service_id"),
            @Result(property = "context", column = "context"),
            @Result(property = "activationDate", column = "activation_date"),
            @Result(property = "processed", column = "processed"),
            @Result(property = "traceId", column = "trace_id")
    })
    Delivery selectById(String uuid);

    @Delete("DELETE FROM redelivery.dead_delivery " +
            "WHERE uuid = #{uuid}")
    void delete(Delivery delivery);
}
