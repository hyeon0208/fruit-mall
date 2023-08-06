package com.fruit.mall.admin.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductRegistrationForm {
    private String productName;
    private int price;
    private String sort;
    private int discount;
    private int stock;
    private String description;

    @Builder
    public ProductRegistrationForm(String productName, int price, String sort, int discount, int stock, String description) {
        this.productName = productName;
        this.price = price;
        this.sort = sort;
        this.discount = discount;
        this.stock = stock;
        this.description = description;
    }
}
