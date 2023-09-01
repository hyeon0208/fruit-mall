package com.fruit.mall.cart.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PaymentProductDto {
    private Long userIdNo;
    private List<Long> productIds;
}
