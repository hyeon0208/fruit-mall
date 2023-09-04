package com.fruit.mall.review;

import com.fruit.mall.review.dto.ReviewResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {
    void insertReview(Review review);

    Long selectReviewIdByUserIdAndProductId(@Param("userIdNo") Long userIdNo, @Param("productId") Long productId);

    List<ReviewResDto> selectReviewsByProductId(@Param("productId") Long productId);
}
