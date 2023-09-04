package com.fruit.mall.orderProduct.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentInfoDto {
    private String quotaInfo;
    private String merchantUid;
    private String payMethod;
    private int payPrice;

    @Builder
    public PaymentInfoDto(String quotaInfo, String merchantUid, String payMethod, int payPrice) {
        this.quotaInfo = quotaInfo;
        this.merchantUid = merchantUid;
        this.payMethod = payMethod;
        this.payPrice = payPrice;
    }
}
