package com.fruit.mall.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecentProduct {
    private String imageUrl;
    private Long productId;

    public RecentProduct(String imageUrl, Long productId) {
        this.imageUrl = imageUrl;
        this.productId = productId;
    }
}
