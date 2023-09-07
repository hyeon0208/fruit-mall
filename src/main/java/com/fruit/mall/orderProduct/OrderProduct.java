package com.fruit.mall.orderProduct;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderProduct {
    private Long orderProductId;
    private Long orderId;
    private Long productId;
    private String orderNumber;
    private int orderCount;
    private int orderPrice;

    @Builder
    public OrderProduct(Long orderId, Long productId, String orderNumber, int orderCount, int orderPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderNumber = orderNumber;
        this.orderCount = orderCount;
        this.orderPrice = orderPrice;
    }
}
