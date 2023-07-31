package com.fruit.mall.user;

import com.fruit.mall.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserLoginController {

    private final UserService userService;
    private final static String LOGIN_USER = "authentic";

    @PostMapping("/login")
    public String loginForm(@ModelAttribute User user, HttpServletRequest request) {
        User loginUser = userService.selectUserByUserEmail(user.getUser_email());
        if (!userService.loginCheck(user.getUser_email(),user.getUser_pwd())) {
            // 로그인 실패 시
        }
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, loginUser);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
