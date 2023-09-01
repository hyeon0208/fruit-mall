package com.fruit.mall.orders.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderReqDto {
    private Long productId;
    private String productName;
    private int productPrice;
    private int productCount;
    private int productDiscount;
    private String imageUrl;

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}
