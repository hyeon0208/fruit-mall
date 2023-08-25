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
    private Long productId;
    private int productCount;
    private int productPrice;
    private int productDiscount;
    private String productSaleStatus;
    private Timestamp cartCreatedAt;

    @Builder
    public Cart(Long userIdNo, Long productId, int productCount, int productPrice, int productDiscount, String productSaleStatus) {
        this.userIdNo = userIdNo;
        this.productId = productId;
        this.productCount = productCount;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productSaleStatus = productSaleStatus;
    }
}
