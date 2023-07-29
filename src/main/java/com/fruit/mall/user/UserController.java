package com.fruit.mall.user;

import com.fruit.mall.user.dto.Term;
import com.fruit.mall.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final static String LOGIN_USER = "authentic";

    @GetMapping("/join")
    public String joinForm() {
        return "user/join";
    }

    @PostMapping("/login")
    public String loginForm(@ModelAttribute User user, HttpServletRequest request) {
        User loginUser = userService.selectByUserEmail(user.getUser_email());
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

    @PostMapping("/joinUser")
    public String joinConfirm(@ModelAttribute User user, Model model) {
        String email = user.getUser_email();
        userService.insertUser(user);
        model.addAttribute("email", email);

//        Long user_id_no = userService.selectOnlyUserIdNo(email);
//        term.setTerm_id_no(user_id_no);
//        userService.insertFirstTerm(term);
//        userService.insertSecondTerm(term);

        return "user/joinConfirm";
    }
}
