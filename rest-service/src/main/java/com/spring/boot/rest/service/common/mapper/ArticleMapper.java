package com.spring.boot.rest.service.common.mapper;

import com.samples.soap.Article;
import com.spring.boot.rest.service.business.entity.ArticleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleDto articleToArticleDto(Article article);
}
