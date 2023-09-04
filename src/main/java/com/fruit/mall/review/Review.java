package com.fruit.mall.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class Review {
    private Long reviewId;
    private Long productId;
    private Long userIdNo;
    private String reviewContents;
    private Timestamp reviewCreatedAt;
    private Timestamp reviewUpdatedAt;

    @Builder
    public Review(Long productId, Long userIdNo, String reviewContents, Timestamp reviewCreatedAt, Timestamp reviewUpdatedAt) {
        this.productId = productId;
        this.userIdNo = userIdNo;
        this.reviewContents = reviewContents;
        this.reviewCreatedAt = reviewCreatedAt;
        this.reviewUpdatedAt = reviewUpdatedAt;
    }
}
