package com.fruit.mall.product;

import com.fruit.mall.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RecentProductApiController {
    private final ImageService imageService;
    private final RecentProductService recentProductService;

    @PostMapping("/recentproduct/{productId}")
    public String addRecentProduct(@PathVariable("productId") Long productId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String recentImage = imageService.selectProductImageUrlByProductId(productId);
        recentProductService.saveRecentProductToCookie(productId, response, request, recentImage);
        return "success";
    }
}
