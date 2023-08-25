package com.fruit.mall.cart.dto;

import com.fruit.mall.cart.Cart;
import lombok.Getter;

import java.util.List;

@Getter
public class CartAddReqDto {
    private Long productId;
    private Long userIdNo;
    private List<Cart> localCart;
}
