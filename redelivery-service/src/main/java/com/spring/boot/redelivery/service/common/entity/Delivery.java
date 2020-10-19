package com.spring.boot.redelivery.service.common.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Delivery {
    private String uuid;
    private String serviceId;
    private String context;
    private LocalDateTime activationDate;
}
