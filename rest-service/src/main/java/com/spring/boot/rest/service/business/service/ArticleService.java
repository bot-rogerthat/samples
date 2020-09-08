package com.spring.boot.rest.service.business.service;

import com.samples.soap.Article;
import com.spring.boot.rest.service.business.entity.ArticleDto;
import com.spring.boot.rest.service.common.mapper.ArticleMapper;
import com.spring.boot.rest.service.integration.ArticleSoapClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleSoapClient articleSoapClient;
    private final ArticleMapper articleMapper;

    public ArticleDto getByName(String name) {
        Article article = articleSoapClient.getArticle(name);
        return articleMapper.articleToArticleDto(article);
    }
}
