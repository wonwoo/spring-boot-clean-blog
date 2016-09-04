package me.wonwoo.comment;

import lombok.Data;
import lombok.Getter;
import me.wonwoo.post.Post;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Comment {
    @Id @GeneratedValue
    private Long id;

    private String content;

    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    public Comment(String content, Post post){
        this.content = content;
        this.post = post;
    }
    Comment(){

    }
}
