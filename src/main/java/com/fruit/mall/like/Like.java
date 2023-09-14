package com.fruit.mall.like;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Like {
    private Long likeId;

    private Long productId;
    private Long userIdNo;

    public Like(Long productId, Long userIdNo) {
        this.productId = productId;
        this.userIdNo = userIdNo;
    }
}
