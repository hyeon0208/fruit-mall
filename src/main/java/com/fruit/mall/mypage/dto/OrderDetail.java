package com.fruit.mall.mypage.dto;

import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
public class OrderDetail {
    private String orderNumber;
    private Long productId;
    private String productName;
    private int orderProductPrice;
    private int orderProductCount;
    private String receiverName;
    private String phoneNumber;
    private String address;
    private String orderRequired;
    private String productImage;
    private Boolean isWrite;
    private Timestamp orderDate;
}
