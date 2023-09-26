package hello.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import static org.springframework.util.StringUtils.hasText;

@Getter
public class PostUpdateDto {

    @NotNull
    private Long postId;

    private String title;
    private String content;

    public boolean updatable() {
        return hasText(title) || hasText(content);
    }

    @Builder
    public PostUpdateDto(Long postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }
}
