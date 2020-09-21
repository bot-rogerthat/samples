package com.spring.boot.kafka.consumer.service.api.kafka;

import com.spring.boot.kafka.consumer.service.business.entity.Article;
import com.spring.boot.kafka.consumer.service.integration.ArticleRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ArticleRestService articleRestService;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group.id}")
    public void consume(Article article) {
        log.info("before consume: {}", article);
        articleRestService.send(article);
        log.info("after consume: {}", article);
    }
}
