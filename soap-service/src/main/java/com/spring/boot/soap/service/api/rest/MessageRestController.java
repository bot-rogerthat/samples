package com.spring.boot.soap.service.api.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1")
public class MessageRestController {

    @PostMapping()
    @RequestMapping("message")
    public String receive(@RequestBody String message) {
        log.info("before receive: {}", message);
        message = message + " accepted";
        log.info("after receive: {}", message);
        return message;
    }
}
