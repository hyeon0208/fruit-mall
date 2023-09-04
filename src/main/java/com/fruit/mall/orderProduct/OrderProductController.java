package com.fruit.mall.orderProduct;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.orderProduct.dto.PaymentInfoDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class OrderProductController {

    @GetMapping("/confirm/payment")
    public String confirmPayment(@Login SessionUser sessionUser,  Model model, @ModelAttribute PaymentInfoDto paymentInfoDto) {
        return "user/paymentSuccess";
    }
}