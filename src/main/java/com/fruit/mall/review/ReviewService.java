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
        PageHelper.startPage(pageNum, pageSize, "REVIEW_CREATED_AT DESC");
        List<ReviewResDto> reviews = reviewRepository.selectReviewsByProductId(productId);
        return new PageInfo<>(reviews);
    }

    public Boolean isWriteReview(Long userIdNo, Long productId) {
        int orderCount = reviewRepository.selectOrderCountByUserIdAndProductId(userIdNo, productId);
        int writeReviewCount = reviewRepository.selectReviewCountByUserIdAndProductId(userIdNo, productId);
        if (orderCount != writeReviewCount) {
            return false;
        }
        return true;
    }

    public void updateReviewByUserId(String updateContents, Long userIdNo) {
        updateContents = updateContents.substring(1, updateContents.length() - 1);
        reviewRepository.updateReviewByUserId(updateContents, userIdNo);
    }
}
