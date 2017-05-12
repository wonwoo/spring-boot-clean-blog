package me.wonwoo.index;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import me.wonwoo.config.Navigation;
import me.wonwoo.config.Section;
import me.wonwoo.post.Post;
import me.wonwoo.post.PostRepository;
import me.wonwoo.post.PostStatus;

import static org.springframework.data.domain.ExampleMatcher.matching;

/**
 * Created by wonwoo on 2016. 9. 10..
 */

@Controller
@RequiredArgsConstructor
@Navigation(Section.HOME)
public class IndexController {

  private final PostRepository postRepository;

  @GetMapping("/")
  public String home(@RequestParam(required = false) String q, Model model, @PageableDefault(size = 5, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable){
    Example<Post> post = Example.of(new Post(q, null , null, PostStatus.Y, null,null),
      matching()
        .withMatcher("title", ExampleMatcher.GenericPropertyMatcher::contains));
    model.addAttribute("posts", postRepository.findAll(post, pageable));
    return "index";
  }
}
