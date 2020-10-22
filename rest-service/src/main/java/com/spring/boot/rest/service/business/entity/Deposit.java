package com.spring.boot.rest.service.business.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Deposit {
    private Long id;
    private String accountName;
    private BigDecimal balance;
    private LocalDateTime expirationDate;
}
