package hello.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentReplyInsertDto {
    @NotNull
    private Long postId;

    @NotNull
    private Long parentCommentId;

    @NotBlank(message = "대댓글 내용을 입력해주세요.")
    private String content;

    public CommentReplyInsertDto(Long postId, Long parentCommentId, String content) {
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }
}
