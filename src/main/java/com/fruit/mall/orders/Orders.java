package com.fruit.mall.orders;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class Orders {
    private Long orderId;
    private Long userIdNo;
    private Long deliveryId;
    private String paymentMethod;
    private int orderPrice;
    private String orderRequired;
    private String orderStatus;
    private Timestamp orderDate;

    @Builder
    public Orders(Long userIdNo, Long deliveryId, String paymentMethod, int orderPrice, String orderRequired, String orderStatus) {
        this.userIdNo = userIdNo;
        this.deliveryId = deliveryId;
        this.paymentMethod = paymentMethod;
        this.orderPrice = orderPrice;
        this.orderRequired = orderRequired;
        this.orderStatus = orderStatus;
    }
}
