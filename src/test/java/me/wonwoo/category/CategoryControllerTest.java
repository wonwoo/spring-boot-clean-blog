package me.wonwoo.category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by wonwoo on 2016. 9. 1..
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
@WithMockUser(username = "wonwoo")
public class CategoryControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CategoryService categoryService;

  @Test
  public void categories() throws Exception {

    List<Category> categories = Arrays.asList(new Category(1L, "spring",LocalDateTime.now()),
      new Category(2L, "jpa", LocalDateTime.now()),
      new Category(3L, "java",LocalDateTime.now()),
      new Category(4L, "spring-boot",LocalDateTime.now()),
      new Category(5L, "javascript",LocalDateTime.now())
    );

    Page<Category> categoryPage = new PageImpl<>(categories);

    given(this.categoryService.findAll(anyObject())).willReturn(categoryPage);
    MvcResult mvcResult = this.mvc.perform(get("/categories").with(csrf()))
      .andExpect(status().isOk())
      .andReturn();

    Page<Category> categoriesPage = (Page<Category>) mvcResult.getModelAndView().getModel().get("categories");
    List<Category> content = categoriesPage.getContent();
    assertThat(content.get(0).getId()).isEqualTo(1L);
    assertThat(content.get(0).getName()).isEqualTo("spring");
    assertThat(content.get(1).getId()).isEqualTo(2L);
    assertThat(content.get(1).getName()).isEqualTo("jpa");
    assertThat(content.get(2).getId()).isEqualTo(3L);
    assertThat(content.get(2).getName()).isEqualTo("java");
    assertThat(content.get(3).getId()).isEqualTo(4L);
    assertThat(content.get(3).getName()).isEqualTo("spring-boot");
    assertThat(content.get(4).getId()).isEqualTo(5L);
    assertThat(content.get(4).getName()).isEqualTo("javascript");
  }

  @Test
  public void newCategory() throws Exception {
    this.mvc.perform(get("/categories/new"))
      .andExpect(status().isOk())
      .andExpect(view().name("category/new"))
      .andReturn();
  }

  @Test
  public void edit() throws Exception {
    given(categoryService.findOne(anyLong())).willReturn(new Category(1L, "spring"));
    MvcResult mvcResult = this.mvc.perform(get("/categories/{id}/edit", 1).with(csrf()))
      .andExpect(status().isOk())
      .andReturn();

    Category category = (Category) mvcResult.getModelAndView().getModel().get("categoryDto");
    assertThat(category.getId()).isEqualTo(1L);
    assertThat(category.getName()).isEqualTo("spring");
  }

  @Test
  public void createCategory() throws Exception {
    Category category = new Category(1L, "spring");
    given(categoryService.createCategory(category)).willReturn(category);

    this.mvc.perform(post("/categories").with(csrf())
      .param("name", "spring"))
      .andExpect(status().isFound())
      .andExpect(header().string(HttpHeaders.LOCATION, "/categories?navSection=Category"));
  }

  @Test
  public void modifyCategory() throws Exception {
    doNothing().when(categoryService).updateCategory(any());

    this.mvc.perform(post("/categories/{id}/edit", 1L).with(csrf())
      .param("name", "spring-boot"))
      .andExpect(status().isFound())
      .andExpect(header().string(HttpHeaders.LOCATION, "/categories?navSection=Category"));
  }

  @Test
  public void deleteCategory() throws Exception {
    doNothing().when(categoryService).delete(any());
    this.mvc.perform(post("/categories/{id}/delete", 1L).with(csrf()))
      .andExpect(status().isFound())
      .andExpect(header().string(HttpHeaders.LOCATION, "/categories?navSection=Category"));
  }
}