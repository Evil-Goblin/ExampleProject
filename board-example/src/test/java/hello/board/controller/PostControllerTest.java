package hello.board.controller;

import hello.board.service.CommentService;
import hello.board.service.MemberService;
import hello.board.service.PageService;
import hello.board.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PageService pageService;

    @MockBean
    CommentService commentService;

    @MockBean
    PostService postService;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("csrf 없이는 403 status 코드가 반환된다.")
    void withoutCsrfTest() throws Exception {
        mockMvc.perform(post("/post/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("title=title&content=content"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    @DisplayName("user 권한과 csrf 가 동시에 있어야 정상처리된다.")
    void withCsrfTest() throws Exception {
        mockMvc.perform(post("/post/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("title=title&content=content"))
                .andExpect(status().isFound());
    }
}
