package me.wonwoo.category;


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
public class CategoryRepositoryTests {
  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  public void findOneTest() {
    final Category persist = this.testEntityManager.persist(new Category("spring"));
    final Category category = this.categoryRepository.findOne(persist.getId());
    assertThat(category.getName()).isEqualTo("spring");
  }
}