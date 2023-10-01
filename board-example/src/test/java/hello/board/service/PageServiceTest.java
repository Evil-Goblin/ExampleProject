package hello.board.service;

import hello.board.dto.*;
import hello.board.entity.Comment;
import hello.board.entity.Post;
import hello.board.repository.CommentRepository;
import hello.board.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
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

        List<Comment> all = commentRepository.findCommentsByPost(postById(postId));
        assertThat(all.size()).isEqualTo(3);

        Long commentId = all.get(1).getId();
        CommentReplyInsertDto replyComment = new CommentReplyInsertDto(postId, commentId, "replyComment");
        commentService.saveReplyComment(replyComment);

        List<Comment> comments = commentRepository.findCommentsByPost(postById(postId));
        assertThat(comments.size()).isEqualTo(4);

        System.out.println("===========REMOVE===========");
        pageService.removePost(postId);

        List<Comment> deletedList = commentRepository.findAll();
        for (Comment comment : deletedList) {
            assertThat(comment.getPost().getId()).isNotEqualTo(postId);
        }
    }

    @Test
    @DisplayName("게시물 정보를 담은 PostResponseDto를 반횐한다.")
    void getPostResponseDto() {
        PostDto postDto = new PostDto("title", "content");
        Long postId = postService.savePost(postDto);

        CommentInsertDto commentContent = new CommentInsertDto(postId, "comment content");
        commentService.saveComment(commentContent);
        commentService.saveComment(commentContent);
        commentService.saveComment(commentContent);

        List<Comment> all = commentRepository.findCommentsByPost(postById(postId));

        Long commentId = all.get(1).getId();
        CommentReplyInsertDto replyComment = new CommentReplyInsertDto(postId, commentId, "replyComment");
        commentService.saveReplyComment(replyComment);

        PostResponseDto postResponseDto = pageService.responsePost(postId, PageRequest.of(0, 2));

        assertThat(postResponseDto.getPostId()).isEqualTo(postId);
        assertThat(postResponseDto.getTitle()).isEqualTo("title");
        assertThat(postResponseDto.getContent()).isEqualTo("content");

        Page<CommentListDto> comments = postResponseDto.getComments();

        assertThat(comments.getSize()).isEqualTo(2);
        assertThat(comments.getTotalElements()).isEqualTo(4);
        assertThat(comments.getTotalPages()).isEqualTo(2);
        assertThat(comments.getNumber()).isEqualTo(0);
        assertThat(comments.getNumberOfElements()).isEqualTo(2);
    }

    private Post postById(Long postId) {
        return postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
    }
}
