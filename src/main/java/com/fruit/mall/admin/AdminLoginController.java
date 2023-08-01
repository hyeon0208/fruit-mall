package com.fruit.mall.admin;

import com.google.api.Http;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public String loginConfirm(@RequestBody Admin inputData, HttpServletRequest request) {
        Admin loginAdmin = adminService.selectAdminById(inputData.getId());
        if (!adminService.loginCheck(inputData.getId(), inputData.getPassword(), loginAdmin)) {
            return "fail";
        }
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, loginAdmin);
        return "success";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/admin/login";
    }
}