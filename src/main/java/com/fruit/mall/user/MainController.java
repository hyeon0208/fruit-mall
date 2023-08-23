package com.fruit.mall.user;

import com.fruit.mall.image.ImageService;
import com.fruit.mall.product.ProductService;
import com.fruit.mall.product.dto.PageResDto;
import com.fruit.mall.product.dto.ProductAndImageInfo;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ProductService productService;
    private final ImageService imageService;
    List<String> recentProducts = new ArrayList<>();

    @GetMapping("favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

    @GetMapping("/")
    public String home(
            Model model,
            HttpServletRequest request,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "9") Integer pageSize) throws UnsupportedEncodingException {

        PageInfo<ProductAndImageInfo> products = productService.getProductsAndImageByFilter(pageNum, pageSize, null, null);
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
            recentProducts = new ArrayList<>(Arrays.asList(decodedRecentProductImages.split(",")));
            model.addAttribute("recentProducts", recentProducts);
        }

        return "user/index";
    }

    @GetMapping("/user/searchfilter")
    @ResponseBody
    public PageResDto userMainSearchFilter(
            @RequestParam HashMap<String, String> params,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "9") Integer pageSize) {
        String category = params.get("category");
        String searchCond = params.get("searchCond");
        PageInfo<ProductAndImageInfo> pageInfo = productService.getProductsAndImageByFilter(pageNum, pageSize, category, searchCond);

        PageResDto pageResDto = new PageResDto(pageInfo, category);

        return pageResDto;
    }

    @PostMapping("/recent-products/{productId}")
    @ResponseBody
    public String addRecentProduct(@PathVariable("productId") Long productId, HttpServletResponse response) throws UnsupportedEncodingException {
        String recentImage = imageService.selectProductImageUrlByProductId(productId);
        if (!recentProducts.contains(recentImage)) {
            recentProducts.add(0, recentImage);
            if (recentProducts.size() > 3) {
                recentProducts.remove(recentProducts.size() - 1);
            }
            String recentProductsCookie = URLEncoder.encode(String.join(",", recentProducts), StandardCharsets.UTF_8.toString());
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
