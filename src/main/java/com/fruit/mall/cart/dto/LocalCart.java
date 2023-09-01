package com.fruit.mall.cart.dto;

import lombok.Getter;

@Getter
public class LocalCart {
    private LocalProduct product;

    @Getter
    public static class LocalProduct {
        private Long productId;
        private int productCount;
        private String productName;
        private int productPrice;
        private int productDiscount;
        private String productSaleStatus;
        private Long imageId;
        private String imageUrl;
    }
}
