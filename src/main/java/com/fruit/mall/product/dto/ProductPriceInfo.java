package com.fruit.mall.product.dto;

import lombok.Getter;

@Getter
public class ProductPriceInfo {
    private int productPrice;
    private int productDiscount;

    public ProductPriceInfo(int productPrice, int productDiscount) {
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
    }
}
