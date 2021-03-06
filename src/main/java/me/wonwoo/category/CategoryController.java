package me.wonwoo.category;

import lombok.RequiredArgsConstructor;
import me.wonwoo.config.Navigation;
import me.wonwoo.config.Section;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;


/**
 * Created by wonwoo on 2016. 8. 24..
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
@Navigation(Section.CATEGORY)
@EnableSpringDataWebSupport
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public String categories(Pageable pageable, Model model) {
    model.addAttribute("categories", categoryService.findAll(pageable));
    return "category/list";
  }

  @GetMapping("/new")
  public String newCategory(@ModelAttribute CategoryDto categoryDto) {
    return "category/new";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Long id, Model model) {
    model.addAttribute("categoryDto", categoryService.findOne(id));
    return "category/edit";
  }

  @PostMapping
  public String createCategory(@ModelAttribute @Valid CategoryDto categoryDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "category/new";
    }
    categoryService.createCategory(new Category(categoryDto.getId(), categoryDto.getName(), LocalDateTime.now()));
    return "redirect:/categories";
  }

  @PostMapping("/{id}/edit")
  public String modifyCategory(@PathVariable Long id, @ModelAttribute @Valid CategoryDto categoryDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "category/edit";
    }
    categoryService.updateCategory(new Category(id, categoryDto.getName()));
    return "redirect:/categories";
  }

  @PostMapping("/{id}/delete")
  public String deleteCategory(@PathVariable Long id) {
    categoryService.delete(id);
    return "redirect:/categories";
  }
}
