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
    public Long selectReviewIdByUserIdAndProductId(Long userIdNo, Long productId) {
        return reviewMapper.selectReviewIdByUserIdAndProductId(userIdNo, productId);
    }

    @Override
    public List<ReviewResDto> selectReviewsByProductId(Long productId) {
        return reviewMapper.selectReviewsByProductId(productId);
    }

    @Override
    public void updateReviewByUserId(String updateContents, Long userIdNo) {
        reviewMapper.updateReviewByUserId(updateContents, userIdNo);
    }
}
