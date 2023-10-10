package hello.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostDto {

    @NotBlank(message = "글 제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "글 내용을 입력해주세요.")
    private String content;

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
