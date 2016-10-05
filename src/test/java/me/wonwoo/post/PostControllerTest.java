package me.wonwoo.post;

import me.wonwoo.category.Category;
import me.wonwoo.category.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by wonwoo on 2016. 8. 31..
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
@WithMockUser(username = "wonwoo")
public class PostControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private PostService postService;

  @MockBean
  private CategoryService categoryService;

  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
    given(categoryService.findAll()).willReturn(Arrays.asList(new Category(1L)));
  }

  @Test
  public void findByPost() throws Exception {
    given(this.postService.findByIdAndStatus(anyLong(), anyObject())).willReturn(new Post("제목", "컨텐츠", "마크다운", PostStatus.Y));
    MvcResult mvcResult = this.mvc.perform(get("/posts/{id}", 1).with(csrf()))
      .andExpect(status().isOk())
      .andReturn();

    Post post = (Post) mvcResult.getModelAndView().getModel().get("post");
    assertThat(post.getTitle()).isEqualTo("제목");
    assertThat(post.getContent()).isEqualTo("컨텐츠");
    assertThat(post.getCode()).isEqualTo("마크다운");
    assertThat(post.getStatus()).isEqualTo(PostStatus.Y);
  }


  @Test
  public void newPost() throws Exception {
    this.mvc.perform(get("/posts/new").with(csrf()))
      .andExpect(status().isOk())
      .andExpect(view().name("post/new"))
      .andReturn();
  }

  @Test
  public void editPost() throws Exception {
    final Post value = new Post("제목", "컨텐츠", "마크다운", PostStatus.Y);
    value.setCategory(new Category(1L, "spring"));
    given(this.postService.findByIdAndStatus(anyLong(), anyObject())).willReturn(value);
    MvcResult mvcResult = this.mvc.perform(get("/posts/edit/{id}", 1).with(csrf()))
      .andExpect(status().isOk())
      .andReturn();

    PostDto postDto = (PostDto) mvcResult.getModelAndView().getModel().get("editPost");
    assertThat(postDto.getTitle()).isEqualTo("제목");
    assertThat(postDto.getContent()).isEqualTo("컨텐츠");
    assertThat(postDto.getCode()).isEqualTo("마크다운");
  }

  @Test
  public void editPostNotFoundException() throws Exception {
    given(this.postService.findByIdAndStatus(1L, PostStatus.Y)).willReturn(new Post("제목", "컨텐츠", "마크다운", PostStatus.Y));
    this.mvc.perform(get("/posts/edit/{id}", 2).with(csrf()))
      .andExpect(status().isNotFound());
  }

  @Test
  public void createPost() throws Exception {
    Post post = new Post(1L, "제목1", "컨텐츠1", "마크다운1", PostStatus.Y);
    given(postService.createPost(any())).willReturn(post);

    this.mvc.perform(post("/posts").with(csrf())
      .param("title", "제목1")
      .param("categoryId", "1")
      .param("content", "컨텐츠1")
      .param("code", "마크다운1"))
      .andExpect(status().isFound())
      .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1?navSection=Post"));
  }

  @Test
  public void createPostValid() throws Exception {
    this.mvc.perform(post("/posts").with(csrf())
      .param("title", "제목1")
      .param("code", "마크다운1"))
      .andExpect(view().name("post/new"));

  }

  @Test
  public void modifyPost() throws Exception {
    Post post = new Post(1L, "제목2", "컨텐츠2", "마크다운2", PostStatus.Y);
    given(postService.updatePost(any(), any())).willReturn(post);

    this.mvc.perform(post("/posts/{id}/edit", 1L).with(csrf())
      .param("title", "제목2")
      .param("categoryId", "1")
      .param("content", "컨텐츠2")
      .param("code", "마크다운2"))
      .andExpect(status().isFound())
      .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1?navSection=Post"));
  }

  @Test
  public void deletePost() throws Exception {
    doNothing().when(postService).deletePost(anyLong());
    this.mvc.perform(post("/posts/{id}/delete", 1L).with(csrf()))
      .andExpect(status().isFound())
      .andExpect(header().string(HttpHeaders.LOCATION, "/?navSection=Post#/"));

  }
//  @EntityScan(basePackageClasses = {SpringBootCleanBlogApplication.class, Jsr310JpaConverters.class})
//  @EnableConfigurationProperties({GitProperties.class})
//  @SpringBootApplication
//  public class TestConfig {
//
//    @Bean
//    public SpringDataDialect springDataDialect() {
//      return new SpringDataDialect();
//    }
//
//    @Bean
//    public RestTemplate restTemplate() {
//      return new RestTemplate();
//    }
//  }
}