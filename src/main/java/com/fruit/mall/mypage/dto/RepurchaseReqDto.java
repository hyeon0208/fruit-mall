package com.fruit.mall.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RepurchaseReqDto {
    private Long productId;
    private int productCount;

    public RepurchaseReqDto(Long productId, int productCount) {
        this.productId = productId;
        this.productCount = productCount;
    }
}
