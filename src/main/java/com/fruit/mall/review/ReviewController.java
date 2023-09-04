package com.fruit.mall.review;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.product.ProductService;
import com.fruit.mall.product.dto.ProductDetailForm;
import com.fruit.mall.review.dto.ReviewSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ProductService productService;
    private final ReviewService reviewService;

    @GetMapping("/user/review/{productId}")
    public String goReview(@Login SessionUser sessionUser, Model model, @PathVariable("productId") Long productId) {
        Long userId = sessionUser != null ? sessionUser.getUserIdNo() : null;
        ProductDetailForm productDetailForm = productService.selectProductDetailByProductId(productId, userId);
        if (userId != null) {
            Boolean isOrder = reviewService.existsOrderProductByUser(userId, productId);
            Boolean isWrite = reviewService.isWriteReview(userId, productId);
            model.addAttribute("isOrder", isOrder);
            model.addAttribute("isWrite", isWrite);
        }
        model.addAttribute("productDetailForm", productDetailForm);
        model.addAttribute("productId", productId);
        return "user/review";
    }

    @PostMapping("/add/review")
    @ResponseBody
    public String addReview(@Login SessionUser sessionUser, @RequestBody ReviewSaveReqDto reviewSaveReqDto) {
        reviewSaveReqDto.setUserIdNo(sessionUser.getUserIdNo());
        reviewService.insertReview(reviewSaveReqDto);
        return "success";
    }
}
