package hello.board.repository;

import hello.board.entity.Comment;
import hello.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    List<Comment> findCommentsByPost(Post post);
}
