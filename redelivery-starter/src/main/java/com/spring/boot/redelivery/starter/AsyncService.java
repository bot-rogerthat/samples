package com.spring.boot.redelivery.starter;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

public interface AsyncService<Req> {

    void onMessage(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment);

    void send(Context<Req> context);
}
