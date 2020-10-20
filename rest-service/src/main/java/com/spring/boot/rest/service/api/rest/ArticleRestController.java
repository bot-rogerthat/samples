package com.spring.boot.rest.service.api.rest;

import com.samples.soap.Article;
import com.samples.soap.GetArticleRequest;
import com.samples.soap.GetArticleResponse;
import com.spring.boot.rest.service.business.entity.ArticleDto;
import com.spring.boot.rest.service.business.service.ArticleService;
import com.spring.boot.rest.service.integration.soap.artice.get.SoapGetArticleSyncService;
import com.spring.boot.rest.service.redelivery.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/articles")
public class ArticleRestController {
    private final DefaultAsyncService<GetArticleRequest, GetArticleResponse> soapGetArticleAsyncService;
    private final SoapGetArticleSyncService soapGetArticleSyncService;
    private final ArticleService articleService;

    @GetMapping("{name}")
    public String getByNameAsync(@PathVariable String name) {
        //todo before log start process

        GetArticleRequest request = new GetArticleRequest();
        request.setName(name);
        Context<GetArticleRequest> context = new Context<>(request);

        soapGetArticleAsyncService.send(context);
        //todo after log end process
        return "ok";
    }

//    @GetMapping("{name}")
//    public Article getByNameSync(@PathVariable String name) throws RedeliveryException {
//        GetArticleRequest request = new GetArticleRequest();
//        request.setName(name);
//        Context<GetArticleRequest> context = new Context<>(request);
//
//        GetArticleResponse response = soapGetArticleSyncService.send(context);
//        return response.getArticle();
//    }

    @PostMapping()
    public ArticleDto add(@RequestBody ArticleDto articleDto) {
        log.info("before add: {}", articleDto);
        ArticleDto added = articleService.add(articleDto);
        log.info("after add: {}", added);
        return added;
    }
}
