package hello.board.controller;

import hello.board.dto.PostListDto;
import hello.board.dto.SearchCond;
import hello.board.service.PageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/search")
    public String searchPosts(@ModelAttribute @Valid SearchCond searchCond, Pageable pageable, Model model) {
        Page<PostListDto> postListDtos = pageService.searchPosts(searchCond, pageable);

        model.addAttribute("posts", postListDtos);

        return "postlist";
    }
}
