package hello.board.repository;

import hello.board.dto.PostListDto;
import hello.board.dto.SearchBy;
import hello.board.dto.SearchCond;
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

    @Test
    @DisplayName("제목으로 검색")
    void searchByTitleTest() {
        Post postA = new Post("AAAtitleA", "AAAcontentA");
        Post postB = new Post("BBBtitleB", "BBBcontentB");
        Post postC = new Post("CCCtitleC", "CCCcontentC");
        Post postD = new Post("DDDtitleD", "DDDcontentD");
        Post postE = new Post("EEEtitleE", "EEEcontentE");
        Post postEE = new Post("EEEEtitleE", "EEEEcontentE");
        Post postEEE = new Post("EEEEEtitleE", "EEEEEcontentE");
        Post postF = new Post("FFFtitleF", "FFFcontentF");
        Post postG = new Post("GGGtitleG", "GGGcontentG");
        Post postH = new Post("HHHtitleH", "HHHcontentH");
        Post postI = new Post("IIItitleI", "IIIcontentI");
        Post postJ = new Post("JJJtitleJ", "JJJcontentJ");
        Post postK = new Post("KKKtitleK", "KKKcontentK");

        postRepository.save(postA);
        postRepository.save(postB);
        postRepository.save(postC);
        postRepository.save(postD);
        postRepository.save(postE);
        postRepository.save(postEE);
        postRepository.save(postEEE);
        postRepository.save(postF);
        postRepository.save(postG);
        postRepository.save(postH);
        postRepository.save(postI);
        postRepository.save(postJ);
        postRepository.save(postK);

        SearchCond cond = new SearchCond(SearchBy.TITLE, "EEE");
        cond.updateWordLike();

        Page<PostListDto> postListBySearchCond = postRepository.findPostListBySearchCond(cond, PageRequest.of(0, 5));
        long totalElements = postListBySearchCond.getTotalElements();
        int totalPages = postListBySearchCond.getTotalPages();
        int numberOfElements = postListBySearchCond.getNumberOfElements();
        int number = postListBySearchCond.getNumber();

        assertThat(totalElements).isEqualTo(3);
        assertThat(totalPages).isEqualTo(1);
        assertThat(numberOfElements).isEqualTo(3);
        assertThat(number).isEqualTo(0);
    }

    @Test
    @DisplayName("내용으로 검색")
    void searchByContentTest() {
        Post postA = new Post("AAAtitleA", "AAAcontentA");
        Post postB = new Post("BBBtitleB", "BBBcontentB");
        Post postC = new Post("CCCtitleC", "CCCcontentC");
        Post postD = new Post("DDDtitleD", "DDDcontentD");
        Post postE = new Post("EEEtitleE", "EEEcontentE");
        Post postEE = new Post("EEEEtitleE", "EEEEcontentE");
        Post postEEE = new Post("EEEEEtitleE", "EEEEEcontentE");
        Post postF = new Post("FFFtitleF", "FFFcontentF");
        Post postG = new Post("GGGtitleG", "GGGcontentG");
        Post postH = new Post("HHHtitleH", "HHHcontentH");
        Post postI = new Post("IIItitleI", "IIIcontentI");
        Post postJ = new Post("JJJtitleJ", "JJJcontentJ");
        Post postK = new Post("KKKtitleK", "KKKcontentK");

        postRepository.save(postA);
        postRepository.save(postB);
        postRepository.save(postC);
        postRepository.save(postD);
        postRepository.save(postE);
        postRepository.save(postEE);
        postRepository.save(postEEE);
        postRepository.save(postF);
        postRepository.save(postG);
        postRepository.save(postH);
        postRepository.save(postI);
        postRepository.save(postJ);
        postRepository.save(postK);

        SearchCond cond = new SearchCond(SearchBy.CONTENT, "EEE");
        cond.updateWordLike();

        Page<PostListDto> postListBySearchCond = postRepository.findPostListBySearchCond(cond, PageRequest.of(0, 5));
        long totalElements = postListBySearchCond.getTotalElements();
        int totalPages = postListBySearchCond.getTotalPages();
        int numberOfElements = postListBySearchCond.getNumberOfElements();
        int number = postListBySearchCond.getNumber();

        assertThat(totalElements).isEqualTo(3);
        assertThat(totalPages).isEqualTo(1);
        assertThat(numberOfElements).isEqualTo(3);
        assertThat(number).isEqualTo(0);
    }
}
