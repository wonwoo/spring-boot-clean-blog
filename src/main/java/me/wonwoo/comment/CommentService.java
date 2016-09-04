package me.wonwoo.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  public Comment createComment(Comment comment) {
    comment.setRegDate(LocalDateTime.now());
    return commentRepository.save(comment);
  }

  public void deleteComment(Long commentId) {
    commentRepository.delete(commentId);
  }
}
