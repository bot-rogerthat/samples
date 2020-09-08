package com.spring.boot.soap.service.business.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime dateAdded;

    public ArticleDto(String name, String description, LocalDateTime dateAdded) {
        this.name = name;
        this.description = description;
        this.dateAdded = dateAdded;
    }
}
