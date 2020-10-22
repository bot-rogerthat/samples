package com.spring.boot.rest.service.api.rest;

import com.samples.soap.GetArticleRequest;
import com.samples.soap.GetArticleResponse;
import com.spring.boot.redelivery.starter.Context;
import com.spring.boot.redelivery.starter.DefaultAsyncService;
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
    private final DefaultAsyncService<GetArticleRequest, GetArticleResponse> soapGetArticleAsyncService;
    private final ArticleService articleService;

    @GetMapping("{name}")
    public String getByNameAsync(@PathVariable String name) {
        Context<Object> context = new Context<>(name);
        //todo before log start process
        log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), this.getClass().getSimpleName(), "start process", name);
        GetArticleRequest request = new GetArticleRequest();
        request.setName(name);
        soapGetArticleAsyncService.send(new Context<>(context, soapGetArticleAsyncService.getTopic(), request));
        //todo after log end process
        log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), this.getClass().getSimpleName(), "end process", "ok");
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
