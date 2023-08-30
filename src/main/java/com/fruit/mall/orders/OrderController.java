package com.fruit.mall.orders;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.orders.dto.OrderReqDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/user/order")
    public String orderProducts(@Login SessionUser sessionUser, Model model, @RequestParam String products) {
        List<OrderReqDto> orderReqDtos = new ArrayList<>();
        JSONArray productsArray = new JSONArray(products);

        for (int i = 0; i < productsArray.length(); i++) {
            Long productId = productsArray.getLong(i);
            OrderReqDto orderReqDto = orderService.selectOrderInfosByProductId(productId);
            orderReqDtos.add(orderReqDto);
        }

        model.addAttribute("products", orderReqDtos);
        model.addAttribute("totalSize", orderReqDtos.size());
        return "user/payment";
    }

    @GetMapping("/user/order/one/{productId}/{productCount}")
    public String orderOneProduct(@Login SessionUser sessionUser, Model model, @PathVariable Long productId, @PathVariable int productCount) {
        List<OrderReqDto> orderReqDtos = new ArrayList<>();
        OrderReqDto orderReqDto = orderService.selectOneOrderInfoByProductId(productId, productCount);
        orderReqDtos.add(orderReqDto);

        model.addAttribute("products", orderReqDtos);
        model.addAttribute("totalSize", orderReqDtos.size());
        return "user/payment";
    }
}
