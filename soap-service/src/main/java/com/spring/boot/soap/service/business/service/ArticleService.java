package com.spring.boot.soap.service.business.service;

import com.spring.boot.soap.service.business.entity.ArticleDto;
import com.spring.boot.soap.service.integration.db.ArticleDbMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final ArticleDbMapper articleDbMapper;
    private final Clock clock;

    @NewSpan
    public ArticleDto getByName(String name) {
        log.info("before selectByName: {}", name);
        ArticleDto selected = articleDbMapper.selectByName(name);
        log.info("after selectByName: {}", selected);
        return selected;
    }

    @NewSpan
    public ArticleDto add(String name, String description) {
        ArticleDto articleDto = new ArticleDto(name, description, LocalDateTime.now(clock));
        log.info("before insert: {}", articleDto);
        ArticleDto inserted = articleDbMapper.insert(articleDto);
        log.info("after insert: {}", inserted);
        return inserted;
    }
}
