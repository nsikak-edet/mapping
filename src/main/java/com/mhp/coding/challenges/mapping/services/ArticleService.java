package com.mhp.coding.challenges.mapping.services;

import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> list();
    ArticleDto articleForId(Long id);
    ArticleDto create(ArticleDto articleDto);

}
