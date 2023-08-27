package com.fruit.mall.product.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AddedProductToCartByNoLoginDto {
    private Long productId;
    private String productName;
    private int productCount;
    private int productPrice;
    private int productDiscount;
    private String productSaleStatus;
    private String imageUrl;
    private Long imageId;


    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}


