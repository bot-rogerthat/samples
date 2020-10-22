package com.spring.boot.redelivery.service.integration.db;

import com.spring.boot.redelivery.service.model.Delivery;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;


@Mapper
public interface DeliveryStorageMapper {

    @Insert("INSERT INTO redelivery.delivery(uuid, service_id, context, activation_date, trace_id) " +
            "VALUES (#{uuid}," +
            " #{serviceId}, " +
            " #{context}::jsonb, " +
            " #{activationDate}," +
            " #{traceId})")
    void insert(Delivery delivery);

    @Select("SELECT uuid, service_id, context, activation_date, trace_id " +
            "FROM redelivery.delivery " +
            "WHERE #{now} > activation_date " +
            "LIMIT #{limitRows} FOR UPDATE SKIP LOCKED")
    @Results({
            @Result(property = "uuid", column = "uuid"),
            @Result(property = "serviceId", column = "service_id"),
            @Result(property = "context", column = "context"),
            @Result(property = "activationDate", column = "activation_date"),
            @Result(property = "processed", column = "processed"),
            @Result(property = "traceId", column = "trace_id")
    })
    List<Delivery> select(LocalDateTime now, long limitRows);

    @Delete("DELETE FROM redelivery.delivery " +
            "WHERE uuid = #{uuid}")
    void delete(Delivery delivery);
}
