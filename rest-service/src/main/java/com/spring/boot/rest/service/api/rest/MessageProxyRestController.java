package com.spring.boot.rest.service.api.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/proxy")
public class MessageProxyRestController {
    private final RestTemplate restTemplate;
    @Value("${message.service.url}")
    private String url;

    @PostMapping()
    @RequestMapping("message")
    public String send(@RequestBody String message) {
        log.info("before send: {}", message);
        String received = restTemplate.postForObject(url, message, String.class);
        log.info("after send: {}", received);
        return received;
    }
}
