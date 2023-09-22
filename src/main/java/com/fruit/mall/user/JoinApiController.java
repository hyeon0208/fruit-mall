package com.fruit.mall.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class JoinApiController {
    private final UserService userService;

    @PostMapping("/join")
    public String joinConfirm(@ModelAttribute User user, @RequestParam Boolean term_flag5, @RequestParam Boolean term_flag6, RedirectAttributes redirectAttributes) {
        userService.insertUser(user, term_flag5, term_flag6);
        redirectAttributes.addAttribute("email", user.getUser_email());
        return "redirect:/join/{email}";
    }

    @GetMapping("/api/v1/user/email/{userEmail}")
    @ResponseBody
    public String checkUserEmail(@PathVariable String userEmail) {
        String findEmail = userService.selectEmailByUserEmail(userEmail);
        return findEmail != null ? "이미 가입된 계정입니다." : "";
    }

    @GetMapping("/api/v1/user/name/{userName}")
    @ResponseBody
    public String checkUserName(@PathVariable String userName) {
        String findUsername = userService.selectUserNameByUserName(userName);
        return findUsername != null ? "해당 닉네임은 이미 사용 중 입니다." : "";
    }
}
