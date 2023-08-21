package com.fruit.mall;

import com.fruit.mall.admin.image.ImageService;
import com.fruit.mall.admin.product.ProductService;
import com.fruit.mall.admin.product.dto.ProductAndImageInfo;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ProductService productService;
    private final ImageService imageService;

    private final static String ON_SALE = "판매중";

    @GetMapping("favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

    @GetMapping("/")
    public String home(
            Model model,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "9") Integer pageSize) {

        PageInfo<ProductAndImageInfo> products = productService.getProductAndImageByStatus(pageNum, pageSize, ON_SALE);
        model.addAttribute("pageInfo", products);

        return "user/index";
    }

    @GetMapping("/{pageName}")
    public String goSubPage(@PathVariable String pageName) {
        return "user/" + pageName;
    }
}
