package hello.board.repository;

import hello.board.dto.PostListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<PostListDto> findPostListAll(Pageable pageable);
}
