package hello.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignUpDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public SignUpDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
