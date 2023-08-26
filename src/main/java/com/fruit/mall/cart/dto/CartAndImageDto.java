package com.fruit.mall.cart.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CartAndImageDto {
    private Long cartId;
    private Long userIdNo;
    private Long productId;
    private String productName;
    private int productCount;
    private int productPrice;
    private int productDiscount;
    private String productSaleStatus;
    private String imageUrl;
    private Long imageId;
}
