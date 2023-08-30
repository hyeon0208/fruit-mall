package com.fruit.mall.product;

import com.fruit.mall.product.dto.RecentProduct;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static com.fruit.mall.user.MainController.recentProducts;

@Service
public class RecentProductService {
    public static final String RECENT_PRODUCTS = "recentProducts";
    private static final int MAX_RECENT_PRODUCTS_SIZE = 3;

    public void saveRecentProductToCookie(Long productId, HttpServletResponse response, String recentImage) throws UnsupportedEncodingException {
        if (!recentProducts.contains(recentImage)) {
            recentProducts.add(0, new RecentProduct(recentImage, productId));
            if (recentProducts.size() > MAX_RECENT_PRODUCTS_SIZE) {
                recentProducts.remove(recentProducts.size() - 1);
            }
            String recentProductsCookie = URLEncoder.encode(
                    recentProducts.stream()
                            .map(product -> product.getImageUrl() + ":" + product.getProductId())
                            .collect(Collectors.joining(",")),
                    StandardCharsets.UTF_8.toString()
            );
            Cookie cookie = new Cookie(RECENT_PRODUCTS, recentProductsCookie);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // Cookie 유효기간 1일로 설정
            response.addCookie(cookie);
        }
    }
}
