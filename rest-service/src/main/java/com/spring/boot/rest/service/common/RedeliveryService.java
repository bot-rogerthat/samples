package com.spring.boot.rest.service.common;

import com.spring.boot.rest.service.redelivery.Context;
import com.spring.boot.rest.service.redelivery.Delivery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RedeliveryService {

    public void doDelivery(Context<?> context, long secondToActivate) {
        // new Delivery();
        // send to kafka
        // uuid + toJson(context) + activationDate (now() + настройка)
    }
}
