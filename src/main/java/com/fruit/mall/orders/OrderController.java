package com.fruit.mall.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fruit.mall.annotaion.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.delivery.DeliveryService;
import com.fruit.mall.delivery.dto.DeliveryResDto;
import com.fruit.mall.orders.dto.OrderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final DeliveryService deliveryService;

    @GetMapping("/user/order")
    public String orderProducts(@Login SessionUser sessionUser, Model model, @RequestParam String products) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Long userId = sessionUser.getUserIdNo();
        List<DeliveryResDto> deliveries = deliveryService.deliveryAllByUserId(userId);
        List<Long> productIds = objectMapper.readValue(products, new TypeReference<List<Long>>(){});
        List<OrderInfo> orderReqDtos = productIds.stream()
                .map(id -> orderService.selectOrderInfosByProductId(id, userId))
                .collect(Collectors.toList());

        model.addAttribute("deliveries", deliveries);
        model.addAttribute("products", orderReqDtos);
        model.addAttribute("totalSize", orderReqDtos.size());
        return "user/payment";
    }

    @GetMapping("/user/order/one/{productId}/{productCount}")
    public String orderOneProduct(@Login SessionUser sessionUser, Model model, @PathVariable Long productId, @PathVariable int productCount) {
        List<OrderInfo> orderReqDtos = new ArrayList<>();
        OrderInfo orderReqDto = orderService.selectOneOrderInfoByProductId(productId, productCount, sessionUser.getUserIdNo());
        orderReqDtos.add(orderReqDto);

        Long userId = sessionUser.getUserIdNo();
        List<DeliveryResDto> deliveries = deliveryService.deliveryAllByUserId(userId);
        model.addAttribute("deliveries", deliveries);
        model.addAttribute("products", orderReqDtos);
        model.addAttribute("totalSize", orderReqDtos.size());
        return "user/payment";
    }
}
