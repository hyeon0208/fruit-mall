package com.fruit.mall.review;

import com.fruit.mall.review.dto.ReviewResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {
    void insertReview(Review review);

    List<ReviewResDto> selectReviewsByProductId(@Param("productId") Long productId);

    void updateReviewByUserId(@Param("updateContents") String updateContents, @Param("userIdNo") Long userIdNo);

    int selectOrderCountByUserIdAndProductId(@Param("userIdNo") Long userIdNo, @Param("productId") Long productId);

    int selectReviewCountByUserIdAndProductId(@Param("userIdNo") Long userIdNo, @Param("productId") Long productId);
}
