package hello.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentInsertDto {

    @NotNull
    private Long postId;

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String content;

    public CommentInsertDto(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }
}
