package com.fruit.mall.delivery;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("/delivery/add")
    @ResponseBody
    public String insertDelivery(@Login SessionUser sessionUser, @RequestBody Delivery delivery) {
        deliveryService.insertDelivery(sessionUser.getUserIdNo(), delivery);
        return "success";
    }
}
