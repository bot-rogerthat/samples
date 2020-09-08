package com.spring.boot.rest.service.api.rest;

import com.spring.boot.rest.service.business.entity.ArticleDto;
import com.spring.boot.rest.service.business.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/articles")
@AllArgsConstructor
public class ArticleRestController {
    private final ArticleService articleService;

    @GetMapping("{name}")
    public ArticleDto getById(@PathVariable String name) {
        return articleService.getByName(name);
    }
}
