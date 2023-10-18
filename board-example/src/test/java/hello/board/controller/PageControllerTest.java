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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PageControllerTest {

    public static final String TEST_PATH = "/posts/search";
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
    @WithMockUser
    @DisplayName("검색 조건에 null이 들어갈 수 없다.")
    void conditionNeverNullTest() throws Exception {
        mockMvc.perform(get(TEST_PATH)
                        .param("searchBy", "")
                        .param("word", "test")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(model().attribute("error_title", "searchBy"))
                .andExpect(model().attribute("error_message", "검색 조건은 필수 항목입니다."));
    }

    @Test
    @WithMockUser
    @DisplayName("검색 내용은 빈값일 수 없다.")
    void searchWordMustNotEmptyTest() throws Exception {
        mockMvc.perform(get(TEST_PATH)
                        .param("searchBy", "TITLE")
                        .param("word", "")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(model().attribute("error_title", "word"));

        mockMvc.perform(get(TEST_PATH)
                        .param("searchBy", "TITLE")
                        .param("word", "                ")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(model().attribute("error_title", "word"))
                .andExpect(model().attribute("error_message", "검색어를 입력해 주십시오."));
    }

    @Test
    @WithMockUser
    @DisplayName("검색어의 최소 길이는 2이다.")
    void leastSearchWordTest() throws Exception {
        mockMvc.perform(get(TEST_PATH)
                        .param("searchBy", "TITLE")
                        .param("word", "t")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(model().attribute("error_title", "word"))
                .andExpect(model().attribute("error_message", "2글자 이상 입력해야 합니다."));
    }

    @Test
    @DisplayName("권한이 없는 요청은 401코드를 리턴한다.")
    void raise401Test() throws Exception {
        mockMvc.perform(get(TEST_PATH)
                        .param("searchBy", "")
                        .param("word", "test")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get(TEST_PATH)
                        .param("searchBy", "TITLE")
                        .param("word", "")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get(TEST_PATH)
                        .param("searchBy", "TITLE")
                        .param("word", "                ")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get(TEST_PATH)
                        .param("searchBy", "TITLE")
                        .param("word", "t")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isUnauthorized());
    }
}
