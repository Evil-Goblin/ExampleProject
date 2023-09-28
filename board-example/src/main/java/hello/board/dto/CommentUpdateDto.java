package hello.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class CommentUpdateDto {

    @NotNull
    private Long commentId;

    private String content;

    public boolean updatable() {
        return StringUtils.hasText(content);
    }

    public CommentUpdateDto(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
