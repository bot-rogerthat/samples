package com.spring.boot.rest.service.common;

import com.spring.boot.rest.service.redelivery.Context;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RedeliveryService {

    public void doDelivery(Context<?> context, long secondToActivate) {
        // вызов реста с запросом вида:
        // uuid + toJson(context) + activationDate (now() + настройка)
    }
}
