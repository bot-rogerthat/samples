package com.spring.boot.rest.service.api.rest;

import com.spring.boot.rest.service.business.entity.ArticleDto;
import com.spring.boot.rest.service.business.service.ArticleService;
import com.spring.boot.rest.service.integration.soap.artice.get.SoapGetArticleService;
import com.spring.boot.rest.service.redelivery.Context;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/articles")
public class ArticleRestController {
    private final SoapGetArticleService soapGetArticleService;
    private final ArticleService articleService;

    @GetMapping("{name}")
    public String getByName(@PathVariable String name) {
        //todo before log start process
        soapGetArticleService.send(name);
        //todo after log end process
        return "ok";
    }

    @PostMapping()
    public ArticleDto add(@RequestBody ArticleDto articleDto) {
        log.info("before add: {}", articleDto);
        ArticleDto added = articleService.add(articleDto);
        log.info("after add: {}", added);
        return added;
    }
}
