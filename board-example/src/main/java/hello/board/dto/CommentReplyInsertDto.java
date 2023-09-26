package hello.board.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentReplyInsertDto {
    @NotNull
    private Long postId;

    @NotNull
    private Long parentCommentId;

    @NotEmpty
    private String content;

    public CommentReplyInsertDto(Long postId, Long parentCommentId, String content) {
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }
}
