package com.fruit.mall.review;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {
    void insertReview(Review review);

    List<Long> selectOPIdByOrderIdAndProductId(@Param("userIdNo") Long userIdNo, @Param("productId") Long productId);

    Long selectReviewIdByUserIdAndProductId(@Param("userIdNo") Long userIdNo, @Param("productId") Long productId);
}
