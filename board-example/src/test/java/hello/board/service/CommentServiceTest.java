package hello.board.service;

import hello.board.dto.CommentInsertDto;
import hello.board.dto.CommentReplyInsertDto;
import hello.board.entity.Comment;
import hello.board.entity.Post;
import hello.board.repository.CommentRepository;
import hello.board.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("Post에 댓글을 작성한다.")
    void commentToPostTest() {
        Post post = new Post("title", "content");
        postRepository.save(post);

        String commentString = "comment";
        CommentInsertDto comment = new CommentInsertDto(post.getId(), commentString);
        commentService.saveComment(comment);

        Comment findComment = commentRepository.findAll().get(0);
        assertThat(findComment.getPost().getId()).isEqualTo(post.getId());
        assertThat(findComment.getContent()).isEqualTo(commentString);
    }

    @Test
    @DisplayName("존재하지 않는 postId로 조회시 IllegalArgumentException이 발생한다.")
    void invalidPostIdTest() {
        CommentInsertDto content = new CommentInsertDto(100L, "content");

        assertThatThrownBy(() -> commentService.saveComment(content))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 댓글id에 대댓글을 작성시 IllegalArgumentException이 발생한다.")
    void invalidCommentIdTest() {
        CommentReplyInsertDto content = new CommentReplyInsertDto(100L, 100L, "content");

        assertThatThrownBy(() -> commentService.saveReplyComment(content))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("댓글을 비활성화한다.")
    void deactivateCommentTest() {
        Comment comment = new Comment(null, "content");
        commentRepository.save(comment);

        commentService.deactivateComment(comment.getId());

        Comment findComment = commentRepository.findById(comment.getId()).get();
        assertThat(findComment.isActive()).isFalse();
    }
}
