package hello.board.service;

import hello.board.dto.SignUpDto;
import hello.board.entity.Member;
import hello.board.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceTest {

    public static final String USERNAME = "user";
    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        Member member = Member.builder()
                .username(USERNAME)
                .password("pass")
                .build();

        Mockito.when(memberRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(memberRepository.findByUsername(USERNAME)).thenReturn(Optional.of(member));
        Mockito.when(memberRepository.save(Mockito.any())).thenReturn(null);

        memberService = new MemberService(memberRepository, new BCryptPasswordEncoder());
    }

    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("존재하지 않는 새로운 회원가입은 승인된다.")
    void signUpCompleteTest() {
        SignUpDto signUpDto = new SignUpDto("NotMatched", "password");

        memberService.signUpMember(signUpDto);
    }

    @Test
    @DisplayName("존재하는 회원의 username으로 회원가입시 IllegalStateException이 발생한다.")
    void signUpFailed() {
        SignUpDto signUpDto = new SignUpDto(USERNAME, "password");
        assertThatThrownBy(() -> memberService.signUpMember(signUpDto))
                .isInstanceOf(IllegalStateException.class);
    }
}
