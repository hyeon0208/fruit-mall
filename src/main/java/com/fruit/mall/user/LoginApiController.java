package com.fruit.mall.user;

import com.fruit.mall.annotaion.Login;
import com.fruit.mall.config.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginApiController {

    private final UserService userService;
    public final static String LOGIN_USER = "loginUser";

    @GetMapping("/findPw/{userEmail}")
    @ResponseBody
    public String findPassword(@PathVariable String userEmail) {
        String findEmail = userService.selectEmailByUserEmail(userEmail);
        if (findEmail == null) {
            return "not_found";
        }
        return "success";
    }

    @PatchMapping("/password")
    @ResponseBody
    public String changePassword(@RequestBody User user) {
        String newPassword = user.getUser_pwd();
        String email = user.getUser_email();
        userService.updateNewPassword(email, newPassword);
        return "success";
    }

    @GetMapping("/login-status")
    @ResponseBody
    public boolean isLogin(@Login SessionUser sessionUser) {
        return sessionUser != null;
    }
}
