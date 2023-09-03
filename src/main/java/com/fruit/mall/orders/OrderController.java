package com.fruit.mall.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.delivery.DeliveryService;
import com.fruit.mall.delivery.dto.DeliveryResDto;
import com.fruit.mall.orderProduct.OrderProductService;
import com.fruit.mall.orders.dto.OrderReqDto;
import com.fruit.mall.orders.dto.OrderSaveDto;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final DeliveryService deliveryService;

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secretkey}")
    private String secretKey;
    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }

    @PostMapping("/user/order/add")
    @ResponseBody
    public String addOrder(@Login SessionUser sessionUser, @RequestBody List<OrderSaveDto> orderSaveDtos) {
        Long userId = sessionUser.getUserIdNo();
        orderService.insertOrder(userId, orderSaveDtos);
        return "success";
    }

    @PostMapping("/user/order/verify_iamport/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> verifyIamport(@PathVariable String imp_uid) {
        return iamportClient.paymentByImpUid(imp_uid);
    }

    @GetMapping("/user/order")
    public String orderProducts(@Login SessionUser sessionUser, Model model, @RequestParam String products) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Long userId = sessionUser.getUserIdNo();
        List<DeliveryResDto> deliveries = deliveryService.deliveryAllByUserId(userId);
        List<Long> productIds = objectMapper.readValue(products, new TypeReference<List<Long>>(){});
        List<OrderReqDto> orderReqDtos = productIds.stream()
                .map(id -> orderService.selectOrderInfosByProductId(id))
                .collect(Collectors.toList());

        model.addAttribute("deliveries", deliveries);
        model.addAttribute("products", orderReqDtos);
        model.addAttribute("totalSize", orderReqDtos.size());
        return "user/payment";
    }

    @GetMapping("/user/order/one/{productId}/{productCount}")
    public String orderOneProduct(@Login SessionUser sessionUser, Model model, @PathVariable Long productId, @PathVariable int productCount) {
        List<OrderReqDto> orderReqDtos = new ArrayList<>();
        OrderReqDto orderReqDto = orderService.selectOneOrderInfoByProductId(productId, productCount);
        orderReqDtos.add(orderReqDto);

        Long userId = sessionUser.getUserIdNo();
        List<DeliveryResDto> deliveries = deliveryService.deliveryAllByUserId(userId);
        model.addAttribute("deliveries", deliveries);
        model.addAttribute("products", orderReqDtos);
        model.addAttribute("totalSize", orderReqDtos.size());
        return "user/payment";
    }
}
