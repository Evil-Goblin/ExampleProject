package hello.board.service;

import hello.board.dto.CommentListDto;
import hello.board.dto.PostListDto;
import hello.board.dto.PostResponseDto;
import hello.board.entity.Post;
import hello.board.repository.CommentRepository;
import hello.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PageService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void removePost(Long postId) {
        Post post = postById(postId);
        commentRepository.deleteByPost(post);
        postRepository.delete(post);
    }

    public Page<PostListDto> postList(Pageable pageable) {
        return postRepository.findPostListAll(pageable);
    }

    public PostResponseDto responsePost(Long postId, Pageable pageable) {
        Post post = postById(postId);

        Page<CommentListDto> commentListDtos = commentRepository.commentsOfPost(post, pageable);

        return PostResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .comments(commentListDtos)
                .build();
    }

    private Post postById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
    }
}
