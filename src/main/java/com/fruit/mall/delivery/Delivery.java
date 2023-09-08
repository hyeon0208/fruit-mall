package com.fruit.mall.delivery;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Delivery {
    private Long deliveryId;
    private Long userIdNo;
    private String deliveryName;
    private String userName;
    private String phoneNumber;
    private int zipcode;
    private String address;

    @Builder
    public Delivery(Long deliveryId, Long userIdNo, String deliveryName, String userName, String phoneNumber, int zipcode, String address) {
        this.deliveryId = deliveryId;
        this.userIdNo = userIdNo;
        this.deliveryName = deliveryName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.zipcode = zipcode;
        this.address = address;
    }
}
