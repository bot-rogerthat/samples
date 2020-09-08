package com.spring.boot.soap.service.integration.db;

import com.spring.boot.soap.service.business.entity.ArticleDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleDbMapper {

    @Select("insert into prod.article (name, description, date_added) " +
            "values (#{name}, #{description}, #{dateAdded}) " +
            "returning *")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateAdded", column = "date_added")
    })
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    ArticleDto insert(ArticleDto articleDto);

    @Select("select id, name, description, date_added as dateAdded from prod.article where id = #{id}")
    ArticleDto selectById(Long id);

    @Select("select id, name, description, date_added as dateAdded " +
            "from prod.article " +
            "where name = #{name} " +
            "limit 1")
    ArticleDto selectByName(String name);

    @Select("select id, name, description, date_added as dateAdded from prod.article")
    List<ArticleDto> selectAll();
}
