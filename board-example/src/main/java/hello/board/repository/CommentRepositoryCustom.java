package hello.board.repository;

import hello.board.entity.Comment;
import hello.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {

    Page<Comment> commentsOfPost(Post post, Pageable pageable);
    void saveReplyComment(Comment replyComment);
}
