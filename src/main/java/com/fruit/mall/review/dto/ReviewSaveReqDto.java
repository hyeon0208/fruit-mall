package com.fruit.mall.review.dto;

import com.fruit.mall.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewSaveReqDto {
    private Long productId;
    private Long userIdNo;
    private String reviewContents;

    @Builder
    public ReviewSaveReqDto(Long productId, Long userIdNo, String reviewContents) {
        this.productId = productId;
        this.userIdNo = userIdNo;
        this.reviewContents = reviewContents;
    }

    public Review toEntity(ReviewSaveReqDto reviewSaveReqDto) {
        return Review.builder()
                .productId(reviewSaveReqDto.getProductId())
                .userIdNo(reviewSaveReqDto.getUserIdNo())
                .reviewContents(reviewSaveReqDto.getReviewContents())
                .build();
    }

    public void setUserIdNo(Long userIdNo) {
        this.userIdNo = userIdNo;
    }
}
