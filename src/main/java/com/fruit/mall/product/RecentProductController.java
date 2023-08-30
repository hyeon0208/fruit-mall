package com.fruit.mall.product;

import com.fruit.mall.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;


@Controller
@RequiredArgsConstructor
public class RecentProductController {
    private final ImageService imageService;
    private final RecentProductService recentProductService;



    @PostMapping("/recent-products/{productId}")
    @ResponseBody
    public String addRecentProduct(@PathVariable("productId") Long productId, HttpServletResponse response) throws UnsupportedEncodingException {
        String recentImage = imageService.selectProductImageUrlByProductId(productId);
        recentProductService.saveRecentProductToCookie(productId, response, recentImage);
        return "success";
    }
}
