package me.wonwoo.comment;

import me.wonwoo.category.Category;
import me.wonwoo.post.Post;
import me.wonwoo.post.PostStatus;
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
public class CommentRepositoryTests {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private CommentRepository commentRepository;

  @Test
  public void findOneTest() {
    final User user = this.testEntityManager.persist(new User("test",
      "test",
      "set",
      "https://test"));

    final Category category = this.testEntityManager.persist(new Category("spring"));

    final Post post = this.testEntityManager.persist(new Post("test title", "test content", "test content", PostStatus.Y,  category, user));
    final Comment persist = this.testEntityManager.persist(new Comment("test commnet", post, user));
    final Comment comment = commentRepository.findOne(persist.getId());
    assertThat(comment.getContent()).isEqualTo(comment.getContent());
  }
}