package com.spring.boot.rest.service.redelivery;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;


@Mapper
public interface StorageMapper {

    @Insert("insert into prod.redelivery (uuid, context, activation_date) " +
            "values (#{uuid}, #{context}), #{activationDate}")
    void insert(String uuid, String context, LocalDateTime activationDate);
}
