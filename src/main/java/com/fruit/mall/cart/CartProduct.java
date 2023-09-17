package com.fruit.mall.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class CartProduct {
    private Long cartId;
    private Long productId;
    private int productPrice;
    private int productCount;
    private int productDiscount;
    private Timestamp cartCreatedAt;

    @Builder
    public CartProduct(Long cartId, Long productId, int productPrice, int productCount, int productDiscount, Timestamp cartCreatedAt) {
        this.cartId = cartId;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productCount = productCount;
        this.productDiscount = productDiscount;
        this.cartCreatedAt = cartCreatedAt;
    }
}
