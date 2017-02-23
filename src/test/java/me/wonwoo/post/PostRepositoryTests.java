package me.wonwoo.post;

import me.wonwoo.category.Category;
import me.wonwoo.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoo on 2017. 2. 23..
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTests {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private PostRepository postRepository;


  @Test
  public void findByIdAndStatus() throws Exception {
    final User user = this.testEntityManager.persist(new User("test",
      "test",
      "set",
      "https://test"));

    final Category category = this.testEntityManager.persist(new Category("spring"));

    final Post persist = this.testEntityManager.persist(new Post("test title", "test content", "test content", PostStatus.Y,  category, user));
    Post post = this.postRepository.findByIdAndStatus(persist.getId(), PostStatus.Y);
    assertThat(post.getTitle()).isEqualTo("test title");
    assertThat(post.getContent()).isEqualTo("test content");
    assertThat(post.getCode()).isEqualTo("test content");
    assertThat(post.getStatus()).isEqualTo(PostStatus.Y);
  }

}