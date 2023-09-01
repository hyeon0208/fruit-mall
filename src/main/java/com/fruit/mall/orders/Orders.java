package com.fruit.mall.orders;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class Orders {
    private Long orderId;
    private Long userIdNo;
    private int orderPrice;
    private int orderCount;
    private String receiverName;
    private String phoneNumber;
    private int zipcode;
    private String address;
    private String orderRequired;
    private String orderStatus;
    private String paymentMethod;
    private Timestamp orderDate;

    @Builder
    public Orders(Long userIdNo, int orderPrice, int orderCount, String receiverName, String phoneNumber, int zipcode, String address, String orderRequired, String orderStatus, String paymentMethod, Timestamp orderDate) {
        this.userIdNo = userIdNo;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
        this.receiverName = receiverName;
        this.phoneNumber = phoneNumber;
        this.zipcode = zipcode;
        this.address = address;
        this.orderRequired = orderRequired;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
    }
}
