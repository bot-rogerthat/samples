package com.spring.boot.rest.service.business.service;

import com.samples.soap.Article;
import com.spring.boot.rest.service.business.entity.ArticleDto;
import com.spring.boot.rest.service.common.mapper.ArticleMapper;
import com.spring.boot.rest.service.integration.soap.ArticleSoapClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleSoapClient articleSoapClient;
    private final ArticleMapper articleMapper;

    public ArticleDto getByName(String name) {
        Article article = articleSoapClient.getArticle(name);
        return articleMapper.articleToArticleDto(article);
    }

    public ArticleDto add(ArticleDto articleDto) {
        Article article = articleSoapClient.addArticle(articleDto);
        return articleMapper.articleToArticleDto(article);
    }
}
