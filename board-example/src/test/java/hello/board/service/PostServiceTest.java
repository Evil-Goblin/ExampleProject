package hello.board.service;

import hello.board.dto.PostDto;
import hello.board.dto.PostUpdateDto;
import hello.board.entity.Post;
import hello.board.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("Title만 변경된다.")
    void updateTitleTest() {
        PostDto postDto = new PostDto("title", "content");
        Long postId = postService.savePost(postDto);

        PostUpdateDto changeTitle = PostUpdateDto.builder()
                .postId(postId)
                .title("ChangeTitle")
                .build();
        postService.updatePost(changeTitle);

        Post post = postRepository.findById(postId).get();
        assertThat(post.getTitle()).isEqualTo("ChangeTitle");
        assertThat(post.getContent()).isEqualTo("content");
    }

    @Test
    @DisplayName("Content만 변경된다.")
    void updateContentTest() {
        PostDto postDto = new PostDto("title", "content");
        Long postId = postService.savePost(postDto);

        PostUpdateDto changeTitle = PostUpdateDto.builder()
                .postId(postId)
                .content("ChangeContent")
                .build();
        postService.updatePost(changeTitle);

        Post post = postRepository.findById(postId).get();
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getContent()).isEqualTo("ChangeContent");
    }

    @Test
    @DisplayName("Title과 Content가 변경된다.")
    void updateTitleContentTest() {
        PostDto postDto = new PostDto("title", "content");
        Long postId = postService.savePost(postDto);

        PostUpdateDto changeTitle = PostUpdateDto.builder()
                .postId(postId)
                .title("ChangeTitle")
                .content("ChangeContent")
                .build();
        postService.updatePost(changeTitle);

        Post post = postRepository.findById(postId).get();
        assertThat(post.getTitle()).isEqualTo("ChangeTitle");
        assertThat(post.getContent()).isEqualTo("ChangeContent");
    }
}
