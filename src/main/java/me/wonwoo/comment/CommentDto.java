package me.wonwoo.comment;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    @NotNull
    private Long postId;

    @NotBlank
    private String content;
}
