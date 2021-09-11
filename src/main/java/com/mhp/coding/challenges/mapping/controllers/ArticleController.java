package com.mhp.coding.challenges.mapping.controllers;

import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public ResponseEntity<List<ArticleDto>> list() {
        return ResponseEntity.ok(articleService.list());
    }

    @GetMapping("/{id}")
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity details(@PathVariable Long id) {
        if(articleService.articleForId(id) == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("article not found");
        }else{
            return ResponseEntity.ok(articleService.articleForId(id));
        }
    }

    @PostMapping()
    public ResponseEntity<ArticleDto> create(@RequestBody ArticleDto articleDto) {
        return ResponseEntity.ok(articleService.create(articleDto));
    }

}
