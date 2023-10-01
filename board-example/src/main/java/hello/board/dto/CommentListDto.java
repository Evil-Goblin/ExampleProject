package hello.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class CommentListDto {

    private String content;

    private Long depth;

    @QueryProjection
    public CommentListDto(String content, Long depth) {
        this.content = content;
        this.depth = depth;
    }
}
