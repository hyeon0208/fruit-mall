package com.fruit.mall.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryResDto {
    private String deliveryName;
    private String userName;
    private String phoneNumber;
    private int zipcode;
    private String address;

    @Builder
    public DeliveryResDto(String deliveryName, String userName, String phoneNumber, int zipcode, String address) {
        this.deliveryName = deliveryName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.zipcode = zipcode;
        this.address = address;
    }
}
