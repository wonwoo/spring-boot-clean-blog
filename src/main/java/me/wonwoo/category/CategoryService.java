package me.wonwoo.category;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.wonwoo.exception.NotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.cache.annotation.CachePut;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 24..
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public Category createCategory(Category category) {
    category.setRegDate(LocalDateTime.now());
    return categoryRepository.save(category);
  }

  public void delete(Long id) {
    categoryRepository.delete(id);
  }

  public void updateCategory(Category category) {
    Category oldCategory = categoryRepository.findOne(category.getId());
    if (oldCategory == null) {
      throw new NotFoundException(category.getId() + " not found");
    }
    oldCategory.setName(category.getName());
  }

  @Transactional(readOnly = true)
  @Cacheable("blog.category")
  public Page<Category> findAll(Pageable pageable) {
    log.info("blog.category cache");
    return categoryRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public Category findOne(Long id) {
    return categoryRepository.findOne(id);
  }
}
