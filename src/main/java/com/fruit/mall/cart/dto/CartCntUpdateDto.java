package com.fruit.mall.cart.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CartCntUpdateDto {
    private int productCount;
    private Long productId;
    private Long cartId;
}
