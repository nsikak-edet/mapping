package com.mhp.coding.challenges.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhp.coding.challenges.mapping.controllers.ArticleController;
import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.repositories.ArticleRepository;
import com.mhp.coding.challenges.mapping.services.ArticleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureJsonTesters
@RunWith(SpringRunner.class)
@WebMvcTest(ArticleController.class)
public class ApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ArticleService articleService;

    @Autowired
    private JacksonTester<ArticleDto> articleDto;

    @Autowired
    private JacksonTester<List<ArticleDto>> articles;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getDetailsTest() throws Exception {
        ArticleDto article = new ArticleDto();
        article.setId(50L);
        article.setAuthor("Max Mustermann");
        article.setDescription("Article Description " + 50);
        article.setTitle("Article Nr.: " + 50);

        // given
        given(articleService.articleForId(50L)).willReturn(article);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/article/50")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(articleDto.write(article).getJson());

    }


    @Test
    public void getDetailsNotFoundTest() throws Exception {

        //given
        given(articleService.articleForId(20L)).willReturn(null);

        //when
        MockHttpServletResponse response = mockMvc.perform(
                get("/article/50")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getAllArticlesTest() throws Exception{
        ArticleDto article1 = new ArticleDto();
		article1.setId(50L);
		article1.setAuthor("Max Mustermann");
		article1.setDescription("Article Description " + 50);
		article1.setTitle("Article Nr.: " + 50);

		ArticleDto article2 = new ArticleDto();
		article2.setId(40L);
		article2.setAuthor("Max Mustermann");
		article2.setDescription("Article Description " + 40);
		article2.setTitle("Article Nr.: " + 40);

		List<ArticleDto> records = new ArrayList<>(Arrays.asList(article1, article2));

		//given
        given(articleService.list()).willReturn(records);

        //when
        MockHttpServletResponse response = mockMvc.perform(
                get("/article")
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(articles.write(records).getJson());

    }

    @Test
    public void createArticleTest() throws Exception{
        ArticleDto article = new ArticleDto();
        article.setId(50L);
        article.setAuthor("Max Mustermann");
        article.setDescription("Article Description " + 50);
        article.setTitle("Article Nr.: " + 50);

        given(articleService.create(article)).willReturn(article);

        MockHttpServletResponse response = mockMvc.perform(
                post("/article")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(article))
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());


//        MockHttpServletResponse mockRequest = mockMvc.perform("/patient")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(record));
    }



}
