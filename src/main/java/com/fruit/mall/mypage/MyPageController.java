package com.fruit.mall.mypage;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.mypage.dto.OrderDetail;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/user/mypage")
    public String mypage(@Login SessionUser sessionUser, Model model,
                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PageInfo<OrderDetail> orderDetails = myPageService.getOrderDetailsByUserID(sessionUser.getUserIdNo(), pageNum, pageSize);
        model.addAttribute("orderDetails", orderDetails);
        return "user/mypage";
    }

    @GetMapping("/user/mypage/delivery")
    public String goMyPageDelivery(@Login SessionUser sessionUser, Model model) {
        return "user/mypage_delivery";
    }

    @GetMapping("/user/mypage/userinfo")
    public String goMyPageUserInfo(@Login SessionUser sessionUser, Model model) {
        return "user/mypageEdit";
    }
}