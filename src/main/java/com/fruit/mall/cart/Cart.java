package com.fruit.mall.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class Cart {
    private Long cartId;
    private Long userIdNo;
    private int totalPrice;
    private Timestamp cartUpdatedAt;

    @Builder
    public Cart(Long userIdNo, int totalPrice) {
        this.userIdNo = userIdNo;
        this.totalPrice = totalPrice;
    }
}
