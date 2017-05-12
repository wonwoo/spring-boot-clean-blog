package me.wonwoo.comment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.json.JacksonTester;

import me.wonwoo.junit.MockitoJsonJUnitRunner;
import me.wonwoo.post.Post;
import me.wonwoo.user.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by wonwoo on 2017. 2. 23..
 */
@RunWith(MockitoJsonJUnitRunner.class)
public class CommentServiceTests {

  private JacksonTester<Comment> json;

  @Mock
  private CommentRepository commentRepository;

  private CommentService commentService;

  @Before
  public void setup() {
    commentService = new CommentService(commentRepository);
  }

  @Test
  public void createComment() throws Exception {
    final Comment comment = new Comment("good post", new Post(null,null,null,null,null, null), new User("test" ,"test","test", "test"));
    comment.setId(1L);
    given(commentRepository.save(any(Comment.class)))
      .willReturn(comment);
    final Comment result = commentService.createComment(comment);
    assertThat(this.json.write(result))
      .isEqualToJson("createcomment.json");
  }

  @Test
  public void deleteComment() throws Exception {
    doNothing().when(commentRepository).delete(any(Comment.class));
    commentRepository.delete(1L);
    verify(commentRepository, times(1)).delete(1L);
  }

}