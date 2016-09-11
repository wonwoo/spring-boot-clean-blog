package me.wonwoo.index;

import lombok.RequiredArgsConstructor;
import me.wonwoo.post.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by wonwoo on 2016. 9. 10..
 */

@Controller
@RequiredArgsConstructor
public class IndexController {

  private final PostRepository postRepository;

  @GetMapping("/")
  public String home(Model model, Pageable pageable){
    model.addAttribute("posts", postRepository.findAll(pageable));
    return "index";
  }
}
