package hello.board.service;

import hello.board.dto.CommentInsertDto;
import hello.board.dto.PostDto;
import hello.board.entity.Comment;
import hello.board.repository.CommentRepository;
import hello.board.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("Post를 삭제하면 Post에 대한 댓글들이 모두 삭제된다.")
    void deleteCascadeTest() { // 이거 delete 쿼리가 개별로 나감으로 그냥 직접 구현하는 방법으로 변경해야할 것 같다.
        PostDto postDto = new PostDto("title", "content");
        Long postId = postService.savePost(postDto);

        CommentInsertDto commentContent = new CommentInsertDto(postId, "comment content");
        commentService.saveComment(commentContent);
        commentService.saveComment(commentContent);
        commentService.saveComment(commentContent);

        List<Comment> all = commentRepository.findAll();
        assertThat(all.size()).isEqualTo(3);

        postService.deletePost(postId);

        List<Comment> deletedComments = commentRepository.findAll();
        assertThat(deletedComments.size()).isEqualTo(0);
    }
}
