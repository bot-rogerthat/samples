package com.spring.boot.rest.service.api.rest;

import com.spring.boot.rest.service.integration.kafka.KafkaSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/kafka")
public class KafkaRestController {
    private final KafkaSender kafkaSender;

    @PostMapping
    @RequestMapping("message")
    public String send(@RequestBody String message) {
        kafkaSender.sendMessage(message);
        return "ok";
    }
}
