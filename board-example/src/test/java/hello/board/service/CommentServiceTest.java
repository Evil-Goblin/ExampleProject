package hello.board.service;

import hello.board.dto.CommentInsertDto;
import hello.board.dto.CommentReplyInsertDto;
import hello.board.dto.CommentUpdateDto;
import hello.board.entity.Comment;
import hello.board.entity.Post;
import hello.board.repository.CommentRepository;
import hello.board.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
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

        Comment findComment = commentRepository.findCommentsByPost(post).get(0);
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
        Post post = new Post("title", "content");
        postRepository.save(post);
        Comment comment = new Comment(post, "content"); // post가 null인 경우의 수가 존재하지 않기 때문에 해당 기능이 제공되지는 않지만 테스트의 편의성을 위해 사용하였다. 하지만 이 또한 예외처리가 필요할지 고민 필요
        commentRepository.save(comment);

        commentService.deactivateComment(comment.getId());

        Comment findComment = commentRepository.findById(comment.getId()).get();
        assertThat(findComment.isActive()).isFalse();
    }

    @Test
    @DisplayName("업데이트할 내용이 없다면 업데이트되지 않는다.")
    void updateUsingEmptyStringTest() {
        Post post = new Post("title", "content");
        postRepository.save(post);

        String defaultContent = "content";
        Comment comment = new Comment(post, defaultContent);
        commentRepository.save(comment);

        CommentUpdateDto commentUpdateDto = new CommentUpdateDto(comment.getId(), null);
        commentService.updateComment(commentUpdateDto);

        Comment findComment = commentRepository.findById(comment.getId()).get();
        assertThat(findComment.getContent()).isEqualTo(defaultContent);

        commentUpdateDto = new CommentUpdateDto(comment.getId(), "");
        commentService.updateComment(commentUpdateDto);

        findComment = commentRepository.findById(comment.getId()).get();
        assertThat(findComment.getContent()).isEqualTo(defaultContent);

        commentUpdateDto = new CommentUpdateDto(comment.getId(), "                           ");
        commentService.updateComment(commentUpdateDto);

        findComment = commentRepository.findById(comment.getId()).get();
        assertThat(findComment.getContent()).isEqualTo(defaultContent);
    }

    @Test
    @DisplayName("업데이트할 내용이 있다면 댓글의 내용이 변경된다.")
    void updateContentTest() {
        Post post = new Post("title", "content");
        postRepository.save(post);

        String defaultContent = "content";
        Comment comment = new Comment(post, defaultContent);
        commentRepository.save(comment);

        String updateContent = "updateContent";
        CommentUpdateDto commentUpdateDto = new CommentUpdateDto(comment.getId(), updateContent);
        commentService.updateComment(commentUpdateDto);

        Comment findComment = commentRepository.findById(comment.getId()).get();
        assertThat(findComment.getContent()).isEqualTo(updateContent);
    }
}
