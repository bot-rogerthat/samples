package com.spring.boot.kafka.producer.service.api.rest;

import com.spring.boot.kafka.producer.service.business.entity.Article;
import com.spring.boot.kafka.producer.service.integration.kafka.KafkaSender;
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
    @RequestMapping("article")
    public Article send(@RequestBody Article article) {
        kafkaSender.sendMessage(article);
        return article;
    }
}
