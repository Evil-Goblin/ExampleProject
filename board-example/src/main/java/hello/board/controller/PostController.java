package hello.board.controller;

import hello.board.dto.PostDto;
import hello.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/add")
    public String addPost() {
        return "addpost";
    }

    @PostMapping("/add")
    public String registryPost(PostDto postDto, RedirectAttributes redirectAttributes) {
        Long postId = postService.savePost(postDto);

        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/posts/{postId}";
    }
}
