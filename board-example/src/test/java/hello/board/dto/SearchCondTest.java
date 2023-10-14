package hello.board.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchCondTest {

    @Test
    @DisplayName("updateWordLike를 통해 앞뒤에 %문자가 추가된다.")
    void updateWordLikeTest() {
        SearchCond test = new SearchCond(SearchBy.TITLE, "test");
        test.updateWordLike();

        Assertions.assertThat(test.getWord()).isEqualTo("%test%");

        SearchCond test2 = new SearchCond(SearchBy.TITLE, "%test2");
        test2.updateWordLike();

        Assertions.assertThat(test2.getWord()).isEqualTo("%test2%");

        SearchCond test3 = new SearchCond(SearchBy.TITLE, "test3%");
        test3.updateWordLike();

        Assertions.assertThat(test3.getWord()).isEqualTo("%test3%");

        SearchCond test4 = new SearchCond(SearchBy.TITLE, "test4%");
        test4.updateWordLike();

        Assertions.assertThat(test4.getWord()).isEqualTo("%test4%");
    }
}
