package com.fruit.mall.admin.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ProductAndImageInfo {
    private Long productId;
    private Long categoryId;
    private String productName;
    private int productPrice;
    private int productDiscount;
    private String imageUrl;
    private Long imageId;
}
