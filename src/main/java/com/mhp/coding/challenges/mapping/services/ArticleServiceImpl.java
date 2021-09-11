package com.mhp.coding.challenges.mapping.services;

import com.mhp.coding.challenges.mapping.mappers.ArticleMapper;
import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repository;

    private final ArticleMapper mapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository repository, ArticleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ArticleDto> list() {
        final List<Article> articles = repository.all();
        final List<ArticleDto> articlesDto = articles.stream()
                .map(mapper::map)
                .collect(Collectors.toList());

        //sort article blocks
        articlesDto.forEach(ArticleDto::sort);
        return articlesDto;
    }

    public ArticleDto articleForId(Long id) {
        final Article article = repository.findBy(id);
        ArticleDto articleDto = mapper.map(article);

        //sort article blocks
        articleDto.sort();
        return articleDto;

    }

    public ArticleDto create(ArticleDto articleDto) {
        final Article create = mapper.map(articleDto);
        repository.create(create);

        ArticleDto newArticleDto = mapper.map(create);

        //sort article blocks
        newArticleDto.sort();
        return newArticleDto;
    }

}
