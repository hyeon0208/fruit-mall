package com.fruit.mall.review;

import com.fruit.mall.annotaion.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.orderProduct.OrderProductService;
import com.fruit.mall.product.ProductService;
import com.fruit.mall.review.dto.MyPageReviewReqDto;
import com.fruit.mall.review.dto.ReviewSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping("/review")
    @ResponseBody
    public String addReview(@Login SessionUser sessionUser, @RequestBody ReviewSaveReqDto dto) {
        reviewService.insertReview(sessionUser.getUserIdNo(), dto);
        return "success";
    }

    @PostMapping("/mypage/review")
    @ResponseBody
    public String mypageReviewAdd(@Login SessionUser sessionUser, @RequestBody MyPageReviewReqDto dto) {
        reviewService.insertMypageReview(sessionUser.getUserIdNo(), dto);
        return "success";
    }

    @PatchMapping("/review")
    @ResponseBody
    public String updateReview(@Login SessionUser sessionUser, @RequestBody String updateContents) {
        reviewService.updateReviewByUserId(updateContents, sessionUser.getUserIdNo());
        return "success";
    }
}
