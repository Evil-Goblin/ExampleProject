package hello.board.controller;

import hello.board.dto.SignUpDto;
import hello.board.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (null != error) {
            model.addAttribute("errorMessage", "Login Error");
        }

        return "login";
    }

    @GetMapping("/signup")
    public String singUp(@RequestParam(required = false) String error, Model model) {
        if (null != error) {
            model.addAttribute("errorMessage", error);
        }

        return "signup";
    }

    @PostMapping("/signup")
    public String createMember(@Valid SignUpDto sign, RedirectAttributes redirectAttributes) {
        memberService.signUpMember(sign);

        return "redirect:/login";
    }
}
