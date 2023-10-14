package hello.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class SearchCond {
    @NotNull(message = "검색 조건은 필수 항목입니다.")
    private SearchBy searchBy;

    @NotBlank(message = "검색어를 입력해 주십시오.")
    @Length(min = 2, message = "2글자 이상 입력해야 합니다.")
    private String word;

    public void updateWordLike() {
        if (!word.startsWith("%"))
            word = "%" + word;
        if (!word.endsWith("%"))
            word += "%";
    }
}
