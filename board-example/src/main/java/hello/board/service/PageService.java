package hello.board.service;

import hello.board.entity.Post;
import hello.board.repository.CommentRepository;
import hello.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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

    private Post postById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
