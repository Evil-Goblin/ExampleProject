package hello.board.service;

import hello.board.dto.PostDto;
import hello.board.dto.PostUpdateDto;
import hello.board.entity.Post;
import hello.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long savePost(PostDto postDto) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public void updatePost(PostUpdateDto postUpdateDto) {
        if (postUpdateDto.updatable()) {
            Post post = postById(postUpdateDto);

            if (hasText(postUpdateDto.getTitle())) {
                post.updateTitle(postUpdateDto.getTitle());
            }

            if (hasText(postUpdateDto.getContent())) {
                post.updateContent(postUpdateDto.getContent());
            }
        }
    }

    private Post postById(PostUpdateDto postUpdateDto) {
        return postRepository.findById(postUpdateDto.getPostId())
                .orElseThrow(IllegalArgumentException::new);
    }

}
