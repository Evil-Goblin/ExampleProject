package hello.board.service;

import hello.board.dto.CommentInsertDto;
import hello.board.dto.CommentReplyInsertDto;
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
class PageServiceTest {

    @Autowired
    PageService pageService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("Post를 삭제하면 관련된 Comment가 전부 삭제된다.")
    void deleteCascade() {
        PostDto postDto = new PostDto("title", "content");
        Long postId = postService.savePost(postDto);

        CommentInsertDto commentContent = new CommentInsertDto(postId, "comment content");
        commentService.saveComment(commentContent);
        commentService.saveComment(commentContent);
        commentService.saveComment(commentContent);

        List<Comment> all = commentRepository.findAll();
        assertThat(all.size()).isEqualTo(3);

        Long commentId = all.get(1).getId();
        CommentReplyInsertDto replyComment = new CommentReplyInsertDto(postId, commentId, "replyComment");
        commentService.saveReplyComment(replyComment);

        List<Comment> comments = commentRepository.findAll();
        assertThat(comments.size()).isEqualTo(4);

        System.out.println("===========REMOVE===========");
        pageService.removePost(postId);

        List<Comment> deletedComments = commentRepository.findAll();
        assertThat(deletedComments.size()).isEqualTo(0);
    }
}
