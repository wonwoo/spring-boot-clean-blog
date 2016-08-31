package me.wonwoo.post;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by wonwoo on 2016. 8. 30..
 */
@Data
@EntityListeners(value = AuditingEntityListener.class)
@Entity
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

  private PostStatus status;

  @CreatedDate
  private LocalDateTime regDate;

  Post(){
  }

  public Post(Long id){
    this.id = id;
  }
  public Post(String title, PostStatus status){
    this.title = title;
    this.status = status;
  }
  public Post(Long id, String title, String content, String code, PostStatus status){
    this.id = id;
    this.title = title;
    this.content = content;
    this.code = code;
    this.status = status;
  }

  public Post(String title, String content, String code, PostStatus status){
    this.title = title;
    this.content = content;
    this.code = code;
    this.status = status;
  }
}