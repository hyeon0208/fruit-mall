package com.fruit.mall.delivery;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class Delivery {
    private Long deliveryId;
    private Long userIdNo;

    @NotEmpty(message = "배송지명을 입력하지 않았습니다.")
    private String deliveryName;

    @NotEmpty(message = "이름을 입력하지 않았습니다.")
    private String userName;

    @NotEmpty(message = "휴대폰 번호를 입력하지 않았습니다.")
    private String phoneNumber;

    @NotNull(message = "우편번호를 입력하지 않았습니다.")
    private int zipcode;

    @NotEmpty(message = "주소를 입력하지 않았습니다.")
    private String address;

    @Builder
    public Delivery(Long userIdNo, String deliveryName, String userName, String phoneNumber, int zipcode, String address) {
        this.userIdNo = userIdNo;
        this.deliveryName = deliveryName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.zipcode = zipcode;
        this.address = address;
    }
}
