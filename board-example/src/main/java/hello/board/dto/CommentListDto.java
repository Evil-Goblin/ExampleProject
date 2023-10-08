package hello.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class CommentListDto {

    private Long commentId;

    private String content;

    private Long depth;

    private boolean active;

    @QueryProjection
    public CommentListDto(Long commentId, String content, Long depth, boolean active) {
        this.commentId = commentId;
        this.content = content;
        this.depth = depth;
        this.active = active;
    }
}
