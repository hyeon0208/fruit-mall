package com.fruit.mall.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDetailForm {
    private String productName;
    private int productPrice;
    private int productDiscount;
    private int productStock;
    private String productDescription;
    private String imageUrl;
    private Long imageId;
}
