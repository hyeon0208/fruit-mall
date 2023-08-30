package com.fruit.mall.user;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.product.ProductService;
import com.fruit.mall.product.dto.PageResDto;
import com.fruit.mall.product.dto.ProductAndImageInfo;
import com.fruit.mall.product.dto.RecentProduct;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ProductService productService;
    private final CookieService cookieService;
    public static List<RecentProduct> recentProducts = new ArrayList<>();

    @GetMapping("favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

    @GetMapping("/")
    public String home(
            @Login SessionUser sessionUser,
            Model model,
            HttpServletRequest request,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "9") Integer pageSize) throws UnsupportedEncodingException {
        PageInfo<ProductAndImageInfo> products = productService.getProductsAndImageByFilter(pageNum, pageSize, null, null, sessionUser);
        model.addAttribute("pageInfo", products);
        recentProducts = cookieService.getRecentProductsFromCookies(request.getCookies());
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
