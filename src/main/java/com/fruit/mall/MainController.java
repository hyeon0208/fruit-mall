package com.fruit.mall;

import com.fruit.mall.admin.image.ImageService;
import com.fruit.mall.admin.product.ProductService;
import com.fruit.mall.admin.product.dto.PageResDto;
import com.fruit.mall.admin.product.dto.ProductAndImageInfo;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ProductService productService;
    private final ImageService imageService;
    List<String> recentProducts = new ArrayList<>();
    private final static String ON_SALE = "판매중";

    @GetMapping("favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

    @GetMapping("/")
    public String home(
            Model model,
            HttpSession session,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "9") Integer pageSize) {

        PageInfo<ProductAndImageInfo> products = productService.getProductsAndImageByFilter(pageNum, pageSize, null, null);
        model.addAttribute("pageInfo", products);

        if (session.getAttribute("recentProducts") != null) {
            recentProducts = (List<String>) session.getAttribute("recentProducts");
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
    public String addRecentProduct(@PathVariable("productId") Long productId, HttpSession session) {
        String recentImage = imageService.selectProductImageUrlByProductId(productId);
        if (!recentProducts.contains(recentImage)) {
            recentProducts.add(0, recentImage);
            if (recentProducts.size() > 3) {
                recentProducts.remove(recentProducts.size() - 1);
            }
            session.setAttribute("recentProducts", recentProducts);
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
