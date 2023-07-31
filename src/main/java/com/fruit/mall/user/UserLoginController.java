package com.fruit.mall.user;

import com.fruit.mall.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserLoginController {

    private final UserService userService;
    private final static String LOGIN_USER = "authentic";

    @PostMapping("/user/login")
    @ResponseBody
    public String loginForm(@RequestBody User user, HttpServletRequest request) {
        User loginUser = userService.selectUserByUserEmail(user.getUser_email());
        if (!userService.loginCheck(user.getUser_pwd(), loginUser)) {
            return "이메일 또는 비밀번호가 일치하지 않습니다.";
        }
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, loginUser);
        return "success";
    }

    @PostMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @PostMapping("/user/check-login")
    @ResponseBody
    public String checkLoginPwd(@RequestParam String user_email) {
        String findEmail = userService.selectEmailByUserEmail(user_email);
        return findEmail == null ? "이메일이 존재하지 않습니다." : "";
    }
}
