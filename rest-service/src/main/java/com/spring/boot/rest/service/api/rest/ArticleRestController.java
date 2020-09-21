package com.spring.boot.rest.service.api.rest;

import com.spring.boot.rest.service.business.entity.ArticleDto;
import com.spring.boot.rest.service.business.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ArticleDto add(@RequestBody ArticleDto articleDto) {
        log.info("before add: {}", articleDto);
        ArticleDto added = articleService.add(articleDto);
        log.info("after add: {}", added);
        return added;
    }
}
