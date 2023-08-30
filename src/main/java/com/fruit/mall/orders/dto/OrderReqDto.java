package com.fruit.mall.orders.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class OrderReqDto {

    private String productName;
    private int productPrice;
    private int productCount;
    private int productDiscount;
    private String imageUrl;

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}
