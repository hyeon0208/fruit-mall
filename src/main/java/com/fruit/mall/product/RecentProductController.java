package com.fruit.mall.product;

import com.fruit.mall.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class RecentProductController {
    private final ImageService imageService;
    private final RecentProductService recentProductService;

    @PostMapping("/recent-products/{productId}")
    @ResponseBody
    public String addRecentProduct(@PathVariable("productId") Long productId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String recentImage = imageService.selectProductImageUrlByProductId(productId);
        recentProductService.saveRecentProductToCookie(productId, response, request, recentImage);
        return "success";
    }
}
