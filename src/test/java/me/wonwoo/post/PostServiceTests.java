package me.wonwoo.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by wonwoo on 2017. 2. 23..
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServiceTests {


  @Mock
  private PostRepository postRepository;

  private PostService postService;

  @Before
  public void setup() {
    postService = new PostService(postRepository);
  }


  @Test
  public void createPost() throws Exception {
    final Post post = new Post("post title", "post content",
      "code", PostStatus.Y);
    given(postRepository.save(any(Post.class)))
      .willReturn(post);

    final Post result = postRepository.save(post);
    assertThat(result.getTitle()).isEqualTo("post title");
    assertThat(result.getContent()).isEqualTo("post content");
    assertThat(result.getCode()).isEqualTo("code");
    assertThat(result.getStatus()).isEqualTo(PostStatus.Y);
  }

  @Test
  public void updatePost() throws Exception {
    final Post post = new Post("post title", "post content",
      "code", PostStatus.Y);
    given(postRepository.findByIdAndStatus(any(), any()))
      .willReturn(post);

    final Post result = postService.updatePost(1L, post);
    assertThat(result.getTitle()).isEqualTo("post title");
    assertThat(result.getContent()).isEqualTo("post content");
    assertThat(result.getCode()).isEqualTo("code");
    assertThat(result.getStatus()).isEqualTo(PostStatus.Y);

  }

  @Test
  public void deletePost() throws Exception {
    final Post post = new Post("post title", "post content",
      "code", PostStatus.Y);
    given(postRepository.findByIdAndStatus(any(), any()))
      .willReturn(post);
    postService.deletePost(1L);
    verify(postRepository, times(1))
      .findByIdAndStatus(1L, PostStatus.Y);
  }

  @Test
  public void findByIdAndStatus() throws Exception {
    final Post post = new Post("post title", "post content",
      "code", PostStatus.Y);
    given(postRepository.findByIdAndStatus(any(), any()))
      .willReturn(post);

    final Post result = postService.findByIdAndStatus(1L, PostStatus.Y);
    assertThat(result.getTitle()).isEqualTo("post title");
    assertThat(result.getContent()).isEqualTo("post content");
    assertThat(result.getCode()).isEqualTo("code");
    assertThat(result.getStatus()).isEqualTo(PostStatus.Y);
  }

}