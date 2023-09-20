package com.fruit.mall.user;

import com.fruit.mall.annotaion.Login;
import com.fruit.mall.config.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserLoginController {

    private final UserService userService;
    public final static String LOGIN_USER = "loginUser";

    @PostMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @PostMapping("/findPw")
    @ResponseBody
    public String findPassword(@RequestParam String user_email) {
        String findEmail = userService.selectEmailByUserEmail(user_email);
        if (findEmail == null) {
            return "not_found";
        }
        return "success";
    }

    @GetMapping("/changePw")
    public String changePw(@RequestParam String user_email, Model model) {
        model.addAttribute("email", user_email);
        return "user/changePw";
    }

    @PostMapping("/changeNewPw")
    @ResponseBody
    public String changePassword(@RequestBody User user) {
        String newPassword = user.getUser_pwd();
        String email = user.getUser_email();
        userService.updateNewPassword(email, newPassword);
        return "success";
    }

    @GetMapping("/api/v1/isLogin")
    @ResponseBody
    public boolean isLogin(@Login SessionUser sessionUser) {
        return sessionUser != null;
    }
}
