package com.fruit.mall.review.dto;

import com.fruit.mall.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageReviewReqDto {
    private Long productId;
    private Long userIdNo;
    private Long orderNumber;
    private String reviewContents;

    public void setUserIdNo(Long userIdNo) {
        this.userIdNo = userIdNo;
    }

    @Builder
    public MyPageReviewReqDto(Long productId, Long userIdNo, Long orderNumber, String reviewContents) {
        this.productId = productId;
        this.userIdNo = userIdNo;
        this.orderNumber = orderNumber;
        this.reviewContents = reviewContents;
    }

    public Review toEntity(MyPageReviewReqDto dto) {
        return Review.builder()
                .productId(dto.getProductId())
                .userIdNo(dto.getUserIdNo())
                .reviewContents(dto.getReviewContents())
                .build();
    }
}
