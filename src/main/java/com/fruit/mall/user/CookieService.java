package com.fruit.mall.user;

import com.fruit.mall.product.dto.RecentProduct;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.fruit.mall.product.RecentProductService.RECENT_PRODUCTS;

@Service
public class CookieService {
    public List<RecentProduct> getRecentProductsFromCookies(Cookie[] cookies) throws UnsupportedEncodingException {
        String recentProductsCookie = getCookieValue(cookies, RECENT_PRODUCTS);

        if (recentProductsCookie != null) {
            String decodedRecentProductImages = URLDecoder.decode(recentProductsCookie, StandardCharsets.UTF_8.toString());
            return Arrays.stream(decodedRecentProductImages.split(","))
                    .map(this::createRecentProduct)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public RecentProduct createRecentProduct(String s) {
        int lastColon = s.lastIndexOf(":");
        String imageUrl = s.substring(0, lastColon);
        Long productId = Long.valueOf(s.substring(lastColon + 1));

        return new RecentProduct(imageUrl, productId);
    }

    public String getCookieValue(Cookie[] cookies, String cookieName) {
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
