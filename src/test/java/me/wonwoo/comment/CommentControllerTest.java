package me.wonwoo.comment;

import me.wonwoo.post.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wonwoo on 2016. 9. 4..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CommentService commentService;

  @Test
  public void createComment() throws Exception {
    Comment comment = new Comment();
    comment.setId(1L);
    comment.setContent("test");
    comment.setPost(new Post(1L));
    given(commentService.createComment(comment)).willReturn(comment);

    this.mvc.perform(post("/comments")
      .param("postId","1")
      .param("content", "test"))
      .andExpect(status().isFound())
      .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1"));
  }

  @Test
  public void deleteComment() throws Exception {
    this.mvc.perform(post("/comments/1/1"))
      .andExpect(status().isFound())
      .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1"));
  }

}