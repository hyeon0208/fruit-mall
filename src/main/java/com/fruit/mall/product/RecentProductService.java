package com.fruit.mall.product;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fruit.mall.product.dto.RecentProduct;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecentProductService {
    private ObjectMapper objectMapper = new ObjectMapper();
    public static final String RECENT_PRODUCTS = "recentProducts";
    private static final int MAX_RECENT_PRODUCTS_SIZE = 3;

    public void saveRecentProductToCookie(Long productId, HttpServletResponse response, HttpServletRequest request, String recentImage) throws IOException {
        List<RecentProduct> recentProducts = getRecentProductsFromCookie(request);

        if (!recentProducts.contains(recentImage)) {
            recentProducts.add(0, new RecentProduct(recentImage, productId));
            if (recentProducts.size() > MAX_RECENT_PRODUCTS_SIZE) {
                recentProducts.remove(recentProducts.size() - 1);
            }

            String recentProductsJson = objectMapper.writeValueAsString(recentProducts);
            String encodedRecentProductsJson = URLEncoder.encode(recentProductsJson, StandardCharsets.UTF_8.toString());

            Cookie cookie = new Cookie(RECENT_PRODUCTS, encodedRecentProductsJson);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // Cookie 유효기간 1일로 설정
            response.addCookie(cookie);
        }
    }

    public List<RecentProduct> getRecentProductsFromCookie(HttpServletRequest request) throws IOException {
        Cookie[] cookies = request.getCookies();
        String recentProductCookieValue = getCookieValue(cookies, RECENT_PRODUCTS);

        if (recentProductCookieValue != null) {
            String decodedCookieValue = URLDecoder.decode(recentProductCookieValue, StandardCharsets.UTF_8.toString());
            return objectMapper.readValue(decodedCookieValue, new TypeReference<List<RecentProduct>>() {});
        }

        return new ArrayList<>();
    }

    private String getCookieValue(Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
