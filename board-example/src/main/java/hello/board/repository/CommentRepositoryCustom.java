package hello.board.repository;

import hello.board.dto.CommentListDto;
import hello.board.entity.Comment;
import hello.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {

    Page<CommentListDto> commentsOfPost(Post post, Pageable pageable);
    void saveReplyComment(Comment replyComment);
    long deleteByPost(Post post);
}
