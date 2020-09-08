package com.spring.boot.soap.service.common.mapper;

import com.samples.soap.Article;
import com.spring.boot.soap.service.business.entity.ArticleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    Article articleDtoToArticle(ArticleDto article);
}
