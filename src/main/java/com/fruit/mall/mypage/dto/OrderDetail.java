package com.fruit.mall.mypage.dto;

import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
public class OrderDetail {
    private Long orderNumber;
    private Long productId;
    private String productName;
    private int orderPrice;
    private int orderCount;
    private String receiverName;
    private String phoneNumber;
    private String address;
    private String orderRequired;
    private String imageUrl;
    private Boolean isWrite;
    private Timestamp orderDate;
}
