package me.wonwoo.post;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wonwoo on 2016. 8. 30..
 */
public interface PostRepository extends JpaRepository<Post, Long> {
  Post findByIdAndStatus(Long id, PostStatus status);
}
