package com.fruit.mall.review;

import com.fruit.mall.review.dto.ReviewResDto;
import com.fruit.mall.review.dto.ReviewSaveReqDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    public PageInfo<ReviewResDto> getReviewsByProductId(Long productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ReviewResDto> reviews = reviewRepository.selectReviewsByProductId(productId);
        return new PageInfo<>(reviews);
    }

    public Boolean isWriteReview(Long userIdNo, Long productId) {
        Long reviewId = reviewRepository.selectReviewIdByUserIdAndProductId(userIdNo, productId);
        if (reviewId == null) {
            return false;
        }
        return true;
    }
}
