package hello.board.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PostResponseDto {

    private Long postId;
    private String title;
    private String content;

    private Page<CommentListDto> comments;

    @Builder
    public PostResponseDto(Long postId, String title, String content, Page<CommentListDto> comments) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.comments = comments;
    }
}
