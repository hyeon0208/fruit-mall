package com.fruit.mall.admin;

import com.fruit.mall.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminLoginController {

    private final AdminService adminService;

    private final static String LOGIN_USER = "authentic";

    @GetMapping("/{pageName}")
    public String goSubPage(@PathVariable String pageName) {
        return "admin/" + pageName;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/index";
    }

    @PostMapping("/login")
    @ResponseBody
    public String loginConfirm(@RequestBody Admin inputData) {
        System.out.println("데이터 : " + inputData);
        Admin findAdmin = adminService.selectAdminById(inputData.getId());
        if (adminService.loginCheck(inputData.getId(), inputData.getPassword(), findAdmin)) {
            return "success";
        }
        return "fail";
    }
}
