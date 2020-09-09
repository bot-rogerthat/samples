package com.spring.boot.soap.service.api.soap;

import com.samples.soap.AddArticleRequest;
import com.samples.soap.AddArticleResponse;
import com.samples.soap.GetArticleRequest;
import com.samples.soap.GetArticleResponse;
import com.spring.boot.soap.service.business.entity.ArticleDto;
import com.spring.boot.soap.service.business.service.ArticleService;
import com.spring.boot.soap.service.common.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
@RequiredArgsConstructor
@Slf4j
public class ArticleEndpoint {
    private static final String NAMESPACE_URI = "http://samples.com/soap";
    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addArticleRequest")
    @ResponsePayload
    public AddArticleResponse addArticle(@RequestPayload AddArticleRequest request) {
        log.info("before addArticle: {}", request);
        ArticleDto articleDto = articleService.add(request.getName(), request.getDescription());
        AddArticleResponse response = new AddArticleResponse();
        response.setArticle(articleMapper.articleDtoToArticle(articleDto));
        log.info("after addArticle: {}", response);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getArticleRequest")
    @ResponsePayload
    public GetArticleResponse getArticle(@RequestPayload GetArticleRequest request) {
        log.info("before getArticle: {}", request);
        ArticleDto articleDto = articleService.getByName(request.getName());
        GetArticleResponse response = new GetArticleResponse();
        response.setArticle(articleMapper.articleDtoToArticle(articleDto));
        log.info("after getArticle: {}", response);
        return response;
    }
}
