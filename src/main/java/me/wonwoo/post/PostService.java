package me.wonwoo.post;

import lombok.RequiredArgsConstructor;
import me.wonwoo.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by wonwoo on 2016. 8. 31..
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;

  public Post createPost(Post post) {
    post.setRegDate(LocalDateTime.now());
    return postRepository.save(post);
  }

  public Post updatePost(Long id, Post post) {
    Post oldPost = postRepository.findByIdAndStatus(id, PostStatus.Y);
    if (oldPost == null) {
      throw new NotFoundException(id + " not found");
    }

    oldPost.setContent(post.getContent());
    oldPost.setCode(post.getCode());
    oldPost.setTitle(post.getTitle());
    return oldPost;
  }

  public void deletePost(Long id) {
    Post oldPost = postRepository.findByIdAndStatus(id, PostStatus.Y);
    if (oldPost == null) {
      throw new NotFoundException(id + " not found");
    }
    oldPost.setStatus(PostStatus.N);
  }

  @Transactional(readOnly = true)
  public Post findByIdAndStatus(Long id, PostStatus status) {
    Post post = postRepository.findByIdAndStatus(id, status);
    if (post == null) {
      throw new NotFoundException(id + " not found");
    }
    return post;
  }
}
