package com.fruit.mall.admin.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class product {
    private Long productId;
    private Long categoryId;
    private String productName;
    private int productPrice;
    private int productStock;
    private String productDescription;
    private int productDiscount;
    private String productSaleStatus;
    private Timestamp productUpdatedAt;
    private Timestamp productCreatedAt;

    @Builder
    public product(Long categoryId, String productName, int productPrice, int productStock, String productDescription, int productDiscount, String productSaleStatus, Timestamp productUpdatedAt, Timestamp productCreatedAt) {
        this.categoryId = categoryId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productDiscount = productDiscount;
        this.productSaleStatus = productSaleStatus;
        this.productUpdatedAt = productUpdatedAt;
        this.productCreatedAt = productCreatedAt;
    }
}
