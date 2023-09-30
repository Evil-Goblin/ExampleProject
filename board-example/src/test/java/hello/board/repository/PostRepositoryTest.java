package hello.board.repository;

import hello.board.dto.PostListDto;
import hello.board.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("유효하지 않은 id에 대한 삭제")
    void removeInvalidId() {
        postRepository.deleteById(100L);
    }

    @Test
    @DisplayName("Projection 을 통해 글 목록 조회")
    void findPostListAllTest() {
        Post postA = new Post("titleA", "contentA");
        Post postB = new Post("titleB", "contentB");
        Post postC = new Post("titleC", "contentC");
        Post postD = new Post("titleD", "contentD");
        Post postE = new Post("titleE", "contentE");
        Post postF = new Post("titleF", "contentF");
        Post postG = new Post("titleG", "contentG");
        Post postH = new Post("titleH", "contentH");
        Post postI = new Post("titleI", "contentI");
        Post postJ = new Post("titleJ", "contentJ");
        Post postK = new Post("titleK", "contentK");

        postRepository.save(postA);
        postRepository.save(postB);
        postRepository.save(postC);
        postRepository.save(postD);
        postRepository.save(postE);
        postRepository.save(postF);
        postRepository.save(postG);
        postRepository.save(postH);
        postRepository.save(postI);
        postRepository.save(postJ);
        postRepository.save(postK);

        Page<PostListDto> postListAll = postRepository.findPostListAll(PageRequest.of(0, 5));

        int size = postListAll.getSize();
        long totalElements = postListAll.getTotalElements();
        int totalPages = postListAll.getTotalPages();
        int numberOfElements = postListAll.getNumberOfElements();
        int number = postListAll.getNumber();

        assertThat(size).isEqualTo(5);
        assertThat(totalElements).isEqualTo(11);
        assertThat(totalPages).isEqualTo(3);
        assertThat(numberOfElements).isEqualTo(5);
        assertThat(number).isEqualTo(0);
    }
}
