package com.spring.boot.rest.service.redelivery;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Delivery {
    private String uuid;
    private String serviceId;
    private String context;
    private LocalDateTime activationDate;
}
