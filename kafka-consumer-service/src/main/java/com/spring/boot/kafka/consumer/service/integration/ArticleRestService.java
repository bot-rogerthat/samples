package com.spring.boot.kafka.consumer.service.integration;

import com.spring.boot.kafka.consumer.service.business.entity.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleRestService {
    private final RestTemplate restTemplate;
    @Value("${article.rest.service.url}")
    private String url;

    public void send(Article article, Acknowledgment acknowledgmen) {
        log.info("before send: {}", article);
        try {
            String result = restTemplate.postForObject(url, article, String.class);
            acknowledgmen.acknowledge();
            log.info("after send: {}", result);
        } catch (Exception e) {
            log.error("error send: {}", e.getMessage());
            throw e;
        }
    }
}
