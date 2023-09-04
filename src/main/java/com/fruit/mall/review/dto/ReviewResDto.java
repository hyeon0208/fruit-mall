package com.fruit.mall.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ReviewResDto {
    private Long reviewId;
    private String userName;
    private String reviewContents;
    private Timestamp reviewCreatedAt;

    @Builder
    public ReviewResDto(Long reviewId, String userName, String reviewContents, Timestamp reviewCreatedAt) {
        this.reviewId = reviewId;
        this.userName = userName;
        this.reviewContents = reviewContents;
        this.reviewCreatedAt = reviewCreatedAt;
    }
}
