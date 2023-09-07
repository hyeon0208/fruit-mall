package com.fruit.mall.review;

import com.fruit.mall.review.dto.ReviewResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository implements ReviewMapper {
    private final ReviewMapper reviewMapper;

    @Override
    public void insertReview(Review review) {
        reviewMapper.insertReview(review);
    }

    @Override
    public void insertMypageReview(Review review, String orderNumber) {
        reviewMapper.insertMypageReview(review, orderNumber);
    }

    @Override
    public List<ReviewResDto> selectReviewsByProductId(Long productId) {
        return reviewMapper.selectReviewsByProductId(productId);
    }

    @Override
    public void updateReviewByUserId(String updateContents, Long userIdNo) {
        reviewMapper.updateReviewByUserId(updateContents, userIdNo);
    }

    @Override
    public int selectOrderCountByUserIdAndProductId(Long userIdNo, Long productId) {
        return reviewMapper.selectOrderCountByUserIdAndProductId(userIdNo, productId);
    }

    @Override
    public int selectReviewCountByUserIdAndProductId(Long userIdNo, Long productId) {
        return reviewMapper.selectReviewCountByUserIdAndProductId(userIdNo, productId);
    }
}
