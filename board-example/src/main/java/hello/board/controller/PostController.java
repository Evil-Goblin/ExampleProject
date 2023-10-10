package hello.board.controller;

import hello.board.dto.PostDto;
import hello.board.dto.PostResponseDto;
import hello.board.service.PageService;
import hello.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {

    private final PostService postService;
    private final PageService pageService;

    @GetMapping("/add")
    public String addPost() {
        return "addpost";
    }

    @PostMapping("/add")
    public String registryPost(@Valid PostDto postDto, RedirectAttributes redirectAttributes) {
        Long postId = postService.savePost(postDto);

        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/post/{postId}";
    }

    @GetMapping("/{postId}")
    public String postItem(@PathVariable Long postId, Pageable pageable, Model model) {
        PostResponseDto postResponseDto = pageService.responsePost(postId, pageable);
        model.addAttribute("responseDto", postResponseDto);

        return "post";
    }
}
