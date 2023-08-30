package com.fruit.mall.orders;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.orders.dto.OrderReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/user/order")
    public String orderProducts(@Login SessionUser sessionUser, Model model) {

        return "user/payment";
    }

    @GetMapping("/user/order/one/{productId}/{productCount}")
    public String orderOneProduct(@Login SessionUser sessionUser, Model model, @PathVariable Long productId, @PathVariable int productCount) {
        List<OrderReqDto> orderReqDtos = orderService.selectOneOrderInfoByProductId(productId, productCount);
        model.addAttribute("products", orderReqDtos);
        model.addAttribute("totalSize", orderReqDtos.size());
        return "user/payment";
    }
}
