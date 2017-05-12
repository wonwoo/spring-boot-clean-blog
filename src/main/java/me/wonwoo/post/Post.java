package me.wonwoo.post;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.wonwoo.category.Category;
import me.wonwoo.comment.Comment;
import me.wonwoo.user.User;

/**
 * Created by wonwoo on 2016. 8. 30..
 */
@Data
@Entity
@ToString(exclude = {"category", "comments"})
@EqualsAndHashCode(exclude = {"category", "comments"})
public class Post {

  @Id
  @GeneratedValue
  private Long id;

  @NotNull
  private String title;

  @Lob
  @NotNull
  private String content;

  @Lob
  private String code;

  @Enumerated(EnumType.STRING)
  private PostStatus status;

  private LocalDateTime regDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CATEGORY_ID")
  private Category category;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
  private List<Comment> comments;

  //// FIXME: 2016. 9. 18. 추가
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private User user;

  Post() {
  }


  public Post(String title, String content, String code, PostStatus status, Category category, User user) {
    this.title = title;
    this.content = content;
    this.code = code;
    this.status = status;
    this.category = category;
    this.user = user;
  }
}