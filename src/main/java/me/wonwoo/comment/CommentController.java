package me.wonwoo.comment;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import me.wonwoo.post.Post;
import me.wonwoo.post.PostRepository;
import me.wonwoo.user.User;


@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;
  private final PostRepository postRepository;

  @ModelAttribute
  public Post post(@ModelAttribute CommentDto commentDto){
    return postRepository.findOne(commentDto.getPostId());
  }

  @PostMapping
  public String createComment(Post post, @ModelAttribute @Valid CommentDto commentDto, BindingResult bindingResult, Model model, @AuthenticationPrincipal User user) {
    if (bindingResult.hasErrors()) {
      return "post/post";
    }
    model.addAttribute("comment", commentService.createComment(
      new Comment(commentDto.getContent(),
              post ,user)
    ));
    return "redirect:/posts/" + commentDto.getPostId();
  }

  @PostMapping("/{postId}/{commentId}")
  public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
    commentService.deleteComment(commentId);
    return "redirect:/posts/" + postId;
  }
}
