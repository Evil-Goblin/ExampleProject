package hello.board.controller;

import hello.board.dto.CommentInsertDto;
import hello.board.dto.CommentReplyInsertDto;
import hello.board.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public String addComment(@Valid CommentInsertDto commentInsertDto, RedirectAttributes redirectAttributes) {
        commentService.saveComment(commentInsertDto);

        redirectAttributes.addAttribute("postId", commentInsertDto.getPostId());

        return "redirect:/post/{postId}";
    }

    @PostMapping("/reply")
    public String addReplyComment(@Valid CommentReplyInsertDto commentReplyInsertDto, RedirectAttributes redirectAttributes) {
        commentService.saveReplyComment(commentReplyInsertDto);

        redirectAttributes.addAttribute("postId", commentReplyInsertDto.getPostId());

        return "redirect:/post/{postId}";
    }
}
