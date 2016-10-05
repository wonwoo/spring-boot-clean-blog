package me.wonwoo.category;

import lombok.*;
import me.wonwoo.post.Post;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Data
@Entity
@ToString(exclude = {"post"})
@EqualsAndHashCode(exclude = {"post"})
public class Category implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private LocalDateTime regDate;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  private List<Post> post = new ArrayList<>();

  Category() {
  }

  public Category(Long id) {
    this.id = id;
  }

  public Category(Long id, String name, LocalDateTime regDate) {
    this.name = name;
    this.id = id;
    this.regDate = regDate;
  }
  public Category(Long id, String name) {
    this.name = name;
    this.id = id;
  }
}
