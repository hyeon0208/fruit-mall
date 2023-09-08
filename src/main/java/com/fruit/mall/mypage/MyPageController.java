package com.fruit.mall.mypage;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.delivery.DeliveryService;
import com.fruit.mall.delivery.dto.DeliveryResDto;
import com.fruit.mall.mypage.dto.OrderDetail;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;
    private final DeliveryService deliveryService;

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
        List<DeliveryResDto> deliveries = deliveryService.deliveryAllByUserId(sessionUser.getUserIdNo());
        model.addAttribute("deliveries", deliveries);
        return "user/mypage_delivery";
    }

    @GetMapping("/user/mypage/userinfo")
    public String goMyPageUserInfo() {
        return "user/mypageEdit";
    }

    @GetMapping("/user/mypage/userinfo/edit")
    public String goMyPageUserInfoEdit() {
        return "user/mypageEdit02";
    }
}
