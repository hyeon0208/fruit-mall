package com.fruit.mall.user;

import com.fruit.mall.cart.CartService;
import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.like.LikeService;
import com.fruit.mall.product.ProductService;
import com.fruit.mall.product.dto.PageResDto;
import com.fruit.mall.product.dto.ProductAndImageInfo;
import com.fruit.mall.product.dto.RecentProduct;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.fruit.mall.product.RecentProductController.RECENT_PRODUCTS;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ProductService productService;
    private final LikeService likeService;
    private final CartService cartService;
    public static List<RecentProduct> recentProducts = new ArrayList<>();

    @GetMapping("favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

    @GetMapping("/")
    public String home(
            Model model,
            @Login SessionUser sessionUser,
            HttpServletRequest request,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "9") Integer pageSize) throws UnsupportedEncodingException {

        PageInfo<ProductAndImageInfo> products = productService.getProductsAndImageByFilter(pageNum, pageSize, null, null, sessionUser);

        if (sessionUser != null) {
            int likesCount = likeService.countLikesByUserId(sessionUser.getUserIdNo());
            model.addAttribute("likesCount", likesCount);

            int userCartsCount = cartService.countCartByUserId(sessionUser.getUserIdNo());
            model.addAttribute("userCartsCount", userCartsCount);
        }
        model.addAttribute("pageInfo", products);

        Cookie[] cookies = request.getCookies();
        String recentProductsCookie = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(RECENT_PRODUCTS)) {
                    recentProductsCookie = cookie.getValue();
                    break;
                }
            }
        }

        if (recentProductsCookie != null) {
            String decodedRecentProductImages = URLDecoder.decode(recentProductsCookie, StandardCharsets.UTF_8.toString());
            recentProducts = Arrays.stream(decodedRecentProductImages.split(","))
                    .map(s -> {
                        int lastColon = s.lastIndexOf(":");
                        String imageUrl = s.substring(0, lastColon);
                        Long productId = Long.valueOf(s.substring(lastColon + 1));
                        return new RecentProduct(imageUrl, productId);
                    })
                    .collect(Collectors.toList());
            model.addAttribute(RECENT_PRODUCTS, recentProducts);
        }

        return "user/index";
    }

    @GetMapping("/user/searchfilter")
    @ResponseBody
    public PageResDto userMainSearchFilter(
            @Login SessionUser sessionUser,
            @RequestParam HashMap<String, String> params,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "9") Integer pageSize) {
        String category = params.get("category");
        String searchCond = params.get("searchCond");
        PageInfo<ProductAndImageInfo> pageInfo = productService.getProductsAndImageByFilter(pageNum, pageSize, category, searchCond, sessionUser);

        if (sessionUser != null) {
            return new PageResDto(pageInfo, category, sessionUser);
        } else {
            return new PageResDto(pageInfo, category, null);
        }
    }

    @GetMapping("/user/mypage")
    public String mypage() {
        return "user/mypage";
    }

    @GetMapping("/{pageName}")
    public String goSubPage(@PathVariable String pageName) {
        return "user/" + pageName;
    }
}
