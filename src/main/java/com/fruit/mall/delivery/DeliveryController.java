package com.fruit.mall.delivery;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.delivery.dto.DeliveryReqDto;
import com.fruit.mall.delivery.dto.DeliveryResDto;
import com.fruit.mall.delivery.dto.DeliveryUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("/delivery/add")
    public String insertDelivery(@Login SessionUser sessionUser, @Validated @RequestBody DeliveryReqDto delivery, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldError().getDefaultMessage();
        }
        Long userId = sessionUser.getUserIdNo();
        deliveryService.insertDelivery(userId, delivery);
        return "success";
    }

    @PostMapping("/delete/delivery")
    @ResponseBody
    public String deleteDelivery(@Login SessionUser sessionUser, @RequestBody Map<String, String> data) {
        deliveryService.deleteDelivery(data.get("deliveryName"), sessionUser.getUserIdNo());
        return "success";
    }

    @PostMapping("/delivery/update")
    @ResponseBody
    public String deleteDelivery(@Login SessionUser sessionUser, @Validated @RequestBody DeliveryUpdateDto delivery, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldError().getDefaultMessage();
        }
        Long userId = sessionUser.getUserIdNo();
        deliveryService.updateDelivery(userId, delivery);
        return "success";
    }

    @GetMapping("/delivery/get/{deliveryName}")
    public DeliveryResDto getDelivery(@Login SessionUser sessionUser, @PathVariable String deliveryName) {
        return deliveryService.selectOneByUserIdAndDeliveryName(sessionUser.getUserIdNo(), deliveryName);
    }
}
