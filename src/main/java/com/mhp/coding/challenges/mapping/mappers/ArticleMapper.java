package com.mhp.coding.challenges.mapping.mappers;

import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ArticleMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public ArticleDto map(Article article){
        return modelMapper.map(article, ArticleDto.class);
    }

    public Article map(ArticleDto articleDto) {
        return modelMapper.map(articleDto, Article.class);
    }
}
