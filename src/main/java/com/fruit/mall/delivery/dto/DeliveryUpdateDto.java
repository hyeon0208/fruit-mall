package com.fruit.mall.delivery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryUpdateDto {
    private String curDeliveryName;
    private String updateDeliveryName;
    private String userName;
    private String phoneNumber;
    private int zipcode;
    private String address;
}
