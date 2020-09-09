package com.spring.boot.rest.service.api.rest;

import com.spring.boot.rest.service.business.entity.ArticleDto;
import com.spring.boot.rest.service.business.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/articles")
public class ArticleRestController {
    private final ArticleService articleService;

    @GetMapping("{name}")
    public ArticleDto getByName(@PathVariable String name) {
        log.info("before getByName: {}", name);
        ArticleDto articleDto = articleService.getByName(name);
        log.info("after getByName: {}", articleDto);
        return articleDto;
    }
}
