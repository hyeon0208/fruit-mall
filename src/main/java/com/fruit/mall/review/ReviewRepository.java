package com.fruit.mall.review;

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
    public List<Long> selectOPIdByOrderIdAndProductId(Long userIdNo, Long productId) {
        return reviewMapper.selectOPIdByOrderIdAndProductId(userIdNo, productId);
    }

    @Override
    public Long selectReviewIdByUserIdAndProductId(Long userIdNo, Long productId) {
        return reviewMapper.selectReviewIdByUserIdAndProductId(userIdNo, productId);
    }
}
