package com.fruit.mall.review;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewMapper {
    void insertReview(Review review);

    Long selectReviewIdByUserIdAndProductId(@Param("userIdNo") Long userIdNo, @Param("productId") Long productId);
}
