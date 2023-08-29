package com.fruit.mall.product;

import com.fruit.mall.image.ImageService;
import com.fruit.mall.product.dto.RecentProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static com.fruit.mall.user.MainController.recentProducts;

@Controller
@RequiredArgsConstructor
public class RecentProductController {
    private final ImageService imageService;

    @PostMapping("/recent-products/{productId}")
    @ResponseBody
    public String addRecentProduct(@PathVariable("productId") Long productId, HttpServletResponse response) throws UnsupportedEncodingException {
        String recentImage = imageService.selectProductImageUrlByProductId(productId);
        if (!recentProducts.contains(recentImage)) {
            recentProducts.add(0, new RecentProduct(recentImage, productId));
            if (recentProducts.size() > 3) {
                recentProducts.remove(recentProducts.size() - 1);
            }
            String recentProductsCookie = URLEncoder.encode(
                    recentProducts.stream()
                            .map(product -> product.getImageUrl() + ":" + product.getProductId())
                            .collect(Collectors.joining(",")),
                    StandardCharsets.UTF_8.toString()
            );
            Cookie cookie = new Cookie("recentProducts", recentProductsCookie);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // Cookie 유효기간 1일로 설정
            response.addCookie(cookie);
        }
        return "success";
    }
}
