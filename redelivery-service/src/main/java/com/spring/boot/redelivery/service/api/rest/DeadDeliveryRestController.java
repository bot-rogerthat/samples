package com.spring.boot.redelivery.service.api.rest;

import com.spring.boot.redelivery.service.integration.db.DeadDeliveryStorageMapper;
import com.spring.boot.redelivery.service.integration.kafka.KafkaSender;
import com.spring.boot.redelivery.service.model.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/delivery")
public class DeadDeliveryRestController {
    private final DeadDeliveryStorageMapper deadDeliveryStorageMapper;
    private final KafkaSender kafkaSender;

    @PostMapping
    @RequestMapping("dead")
    public List<String> send(@RequestBody List<String> deadDeliveries) {
        deadDeliveries.forEach(id -> {
            Delivery delivery = deadDeliveryStorageMapper.selectById(id);
            kafkaSender.send(delivery);
            deadDeliveryStorageMapper.delete(delivery);
        });
        return deadDeliveries;
    }
}
