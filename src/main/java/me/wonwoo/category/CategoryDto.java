package me.wonwoo.category;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by wonwoo on 2016. 8. 24..
 */
@Data
public class CategoryDto {
  private Long id;
  @NotBlank
  private String name;
}
