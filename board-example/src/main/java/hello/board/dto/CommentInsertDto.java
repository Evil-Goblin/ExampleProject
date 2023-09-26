package hello.board.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentInsertDto {

    @NotNull
    private Long postId;

    @NotEmpty
    private String content;

    public CommentInsertDto(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }
}
