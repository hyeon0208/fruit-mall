package com.fruit.mall.delivery;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.delivery.dto.DeliveryResDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("/delivery/add")
    @ResponseBody
    public String insertDelivery(@Login SessionUser sessionUser, @Validated @RequestBody Delivery delivery, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldError().getDefaultMessage();
        }

        Long userId = sessionUser.getUserIdNo();
        deliveryService.insertDelivery(userId, delivery);
        return "success";
    }

    @GetMapping("/delivery/get/{deliveryName}")
    @ResponseBody
    public DeliveryResDto getDelivery(@Login SessionUser sessionUser, @PathVariable String deliveryName) {
        return deliveryService.selectOneByUserIdAndDeliveryName(sessionUser.getUserIdNo(), deliveryName);
    }
}
