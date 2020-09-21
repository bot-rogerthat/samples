package com.spring.boot.rest.service.integration.soap;

import com.samples.soap.*;
import com.spring.boot.rest.service.business.entity.ArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArticleSoapClient {
    private final WebServiceTemplate webServiceTemplate;

    @NewSpan
    public Article getArticle(String name) {
        GetArticleRequest request = new GetArticleRequest();
        request.setName(name);
        log.info("before getArticle: {}", request);
        GetArticleResponse response = (GetArticleResponse) webServiceTemplate.marshalSendAndReceive(request);
        log.info("after getArticle: {}", response);
        return response.getArticle();
    }

    @NewSpan
    public Article addArticle(ArticleDto articleDto) {
        AddArticleRequest request = new AddArticleRequest();
        request.setName(articleDto.getName());
        request.setDescription(articleDto.getDescription());
        log.info("before addArticle: {}", request);
        AddArticleResponse response = (AddArticleResponse) webServiceTemplate.marshalSendAndReceive(request);
        log.info("after addArticle: {}", response);
        return response.getArticle();
    }
}
