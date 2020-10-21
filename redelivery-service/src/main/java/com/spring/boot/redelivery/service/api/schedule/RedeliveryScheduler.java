package com.spring.boot.redelivery.service.api.schedule;

import com.spring.boot.redelivery.service.integration.db.DeliveryStorageMapper;
import com.spring.boot.redelivery.service.integration.kafka.KafkaSender;
import com.spring.boot.redelivery.service.model.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedeliveryScheduler {
    private final DeliveryStorageMapper deliveryStorageMapper;
    private final KafkaSender kafkaSender;
    private final Clock clock;
    @Value("${delivery.limit.rows}")
    private long limitRows;

    @Scheduled(cron = "${cron.expression}")
    @Transactional
    public void run() {
        List<Delivery> deliveries = deliveryStorageMapper.select(LocalDateTime.now(clock), limitRows);
        for (Delivery delivery : deliveries) {
            log.info("before run: {}", delivery);
            kafkaSender.sendMessage(delivery);
            deliveryStorageMapper.delete(delivery);
            log.info("after run: {}", delivery);
        }
    }
}
