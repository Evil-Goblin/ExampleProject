package hello.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class PostDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
