package hello.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PostListDto {
    private Long postId;
    private String title;

    @QueryProjection
    public PostListDto(Long postId, String title) {
        this.postId = postId;
        this.title = title;
    }
}
