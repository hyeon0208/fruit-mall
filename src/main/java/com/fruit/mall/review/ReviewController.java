package com.fruit.mall.review;

import com.fruit.mall.annotaion.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.orderProduct.OrderProductService;
import com.fruit.mall.product.ProductService;
import com.fruit.mall.product.dto.ProductDetailForm;
import com.fruit.mall.review.dto.MyPageReviewReqDto;
import com.fruit.mall.review.dto.ReviewResDto;
import com.fruit.mall.review.dto.ReviewSaveReqDto;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ProductService productService;
    private final ReviewService reviewService;
    private final OrderProductService orderProductService;

    @GetMapping("/user/review/{productId}")
    public String goReview(@Login SessionUser sessionUser, Model model,
                           @PathVariable("productId") Long productId,
                           @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        PageInfo<ReviewResDto> pageInfo = reviewService.getReviewsByProductId(productId, pageNum, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        Long userId = sessionUser != null ? sessionUser.getUserIdNo() : null;
        ProductDetailForm productDetailForm = productService.selectProductDetailByProductId(productId, userId);
        if (userId != null) {
            Boolean isOrder = orderProductService.existsOrderProductByUser(userId, productId);
            Boolean isWrite = reviewService.isWriteReview(userId, productId);
            model.addAttribute("isOrder", isOrder);
            model.addAttribute("isWrite", isWrite);
        }
        model.addAttribute("productDetailForm", productDetailForm);
        model.addAttribute("productId", productId);
        return "user/review";
    }
}
