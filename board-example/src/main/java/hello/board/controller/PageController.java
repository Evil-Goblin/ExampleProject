package hello.board.controller;

import hello.board.dto.PostListDto;
import hello.board.dto.PostResponseDto;
import hello.board.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/posts")
@Controller
public class PageController {

    private final PageService pageService;

    @GetMapping
    public String postList(Pageable pageable, Model model) {
        Page<PostListDto> postListDtos = pageService.postList(pageable);

        model.addAttribute("posts", postListDtos);

        return "postlist";
    }

    @GetMapping("/{postId}")
    public String postItem(@PathVariable Long postId, Pageable pageable, Model model) {
        PostResponseDto postResponseDto = pageService.responsePost(postId, pageable);
        model.addAttribute("responseDto", postResponseDto);

        return "post";
    }
}
