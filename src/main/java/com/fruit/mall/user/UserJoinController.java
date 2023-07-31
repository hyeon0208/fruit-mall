package com.fruit.mall.user;

import com.fruit.mall.user.dto.Term;
import com.fruit.mall.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserJoinController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @PostMapping("/joinUser")
    public String joinConfirm(@ModelAttribute User user, @RequestParam Boolean term_flag5, @RequestParam Boolean term_flag6, Model model) {
        User joinUser = User.builder()
                .user_email(user.getUser_email())
                .user_name(user.getUser_name())
                .user_pwd(passwordEncoder.encode(user.getUser_pwd()))
                .user_status(Role.USER)
                .build();
        userService.insertUser(joinUser);

        String email = joinUser.getUser_email();
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
