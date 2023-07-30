package com.fruit.mall.user;

import com.fruit.mall.user.dto.Term;
import com.fruit.mall.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final static String LOGIN_USER = "authentic";

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
    public String joinConfirm(@ModelAttribute User user, @RequestParam Boolean term_flag5, @RequestParam Boolean term_flag6,  Model model) {
        user.setUser_status(Role.USER);
        userService.insertUser(user);

        String email = user.getUser_email();
        model.addAttribute("email", email);

        Integer termFlag5 = term_flag5 ? 1 : 0;
        Integer termFlag6 = term_flag6 ? 1 : 0;

        Long user_id_no = userService.selectOnlyUserIdNo(email);
        Term term5 = Term.builder()
                .user_id_no(user_id_no)
                .term_name("선택약관5")
                .term_flag(termFlag5)
                .build();

        Term term6 = Term.builder()
                .user_id_no(user_id_no)
                .term_name("선택약관6")
                .term_flag(termFlag6)
                .build();

        userService.insertTerm(term5);
        userService.insertTerm(term6);

        return "user/joinConfirm";
    }
}
