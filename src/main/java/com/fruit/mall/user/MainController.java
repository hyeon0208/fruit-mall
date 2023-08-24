package com.fruit.mall.user;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.image.ImageService;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.fruit.mall.user.UserLoginController.LOGIN_USER;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ProductService productService;
    private final ImageService imageService;
    private final LikeService likeService;
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
        }
        model.addAttribute("pageInfo", products);

        Cookie[] cookies = request.getCookies();
        String recentProductsCookie = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("recentProducts")) {
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
            model.addAttribute("recentProducts", recentProducts);
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

    @GetMapping("/user/mypage")
    public String mypage() {
        return "user/mypage";
    }

    @GetMapping("/{pageName}")
    public String goSubPage(@PathVariable String pageName) {
        return "user/" + pageName;
    }
}
