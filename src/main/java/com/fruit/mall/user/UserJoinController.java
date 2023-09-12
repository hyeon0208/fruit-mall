package com.fruit.mall.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/join")
public class UserJoinController {
    private final UserService userService;

    @PostMapping("/joinUser")
    public String joinConfirm(@ModelAttribute User user, @RequestParam Boolean term_flag5, @RequestParam Boolean term_flag6, RedirectAttributes redirectAttributes) {
        userService.insertUser(user, term_flag5, term_flag6);
        redirectAttributes.addAttribute("email", user.getUser_email());
        return "redirect:/join/joinConfirm?email={email}";
    }

    @GetMapping("/joinConfirm")
    public String joinConfirm(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "user/joinConfirm";
    }

    @PostMapping("/check-email")
    @ResponseBody
    public String checkUserEmail(@RequestParam String user_email) {
        String findEmail = userService.selectEmailByUserEmail(user_email);
        return findEmail != null ? "이미 가입된 계정입니다." : "";
    }

    @PostMapping("/check-name")
    @ResponseBody
    public String checkUserName(@RequestBody HashMap<String, Object> param) {
        String findUsername = userService.selectUserNameByUserName((String) param.get("user_name"));
        return findUsername != null ? "해당 닉네임은 이미 사용 중 입니다." : "";
    }
}
