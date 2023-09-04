package com.fruit.mall.review;

import com.fruit.mall.review.dto.ReviewSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public void insertReview(ReviewSaveReqDto reviewSaveReqDto) {
        Review review = reviewSaveReqDto.toEntity(reviewSaveReqDto);
        reviewRepository.insertReview(review);
    }

    public Boolean existsOrderProductByUser(Long userIdNo, Long productId) {
        List<Long> orderProductId = reviewRepository.selectOPIdByOrderIdAndProductId(userIdNo, productId);
        if (orderProductId.isEmpty()) {
            return false;
        }
        return true;
    }

    public Boolean isWriteReview(Long userIdNo, Long productId) {
        Long reviewId = reviewRepository.selectReviewIdByUserIdAndProductId(userIdNo, productId);
        if (reviewId == null) {
            return false;
        }
        return true;
    }
}
