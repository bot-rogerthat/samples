package com.spring.boot.soap.service.business.service;

import com.spring.boot.soap.service.business.entity.ArticleDto;
import com.spring.boot.soap.service.integration.db.ArticleDbMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleDbMapper articleDbMapper;
    private final Clock clock;

    public ArticleDto getByName(String name) {
        return articleDbMapper.selectByName(name);
    }

    public ArticleDto add(String name, String description) {
        ArticleDto articleDto = new ArticleDto(name, description, LocalDateTime.now(clock));
        return articleDbMapper.insert(articleDto);
    }
}
