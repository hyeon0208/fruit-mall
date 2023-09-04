package com.fruit.mall.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepository implements ReviewMapper {
    private final ReviewMapper reviewMapper;

    @Override
    public void insertReview(Review review) {
        reviewMapper.insertReview(review);
    }
}
