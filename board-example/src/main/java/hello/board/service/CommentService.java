package hello.board.service;

import hello.board.dto.CommentInsertDto;
import hello.board.dto.CommentReplyInsertDto;
import hello.board.entity.Comment;
import hello.board.entity.Post;
import hello.board.repository.CommentRepository;
import hello.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void saveComment(CommentInsertDto commentInsertDto) {
        Post post = postById(commentInsertDto.getPostId());

        Comment comment = Comment.newComment(post, commentInsertDto.getContent());
        commentRepository.save(comment);
    }

    @Transactional
    public void saveReplyComment(CommentReplyInsertDto commentReplyInsertDto) {
        Comment comment = commentById(commentReplyInsertDto.getParentCommentId());

        Comment replyComment = comment.newReplyComment(commentReplyInsertDto.getContent());
        commentRepository.saveReplyComment(replyComment);
    }

    @Transactional
    public void deactivateComment(Long commentId) {
        Comment comment = commentById(commentId);

        comment.deactivate();
    }

    private Post postById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(IllegalArgumentException::new);
    }

    private Comment commentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
