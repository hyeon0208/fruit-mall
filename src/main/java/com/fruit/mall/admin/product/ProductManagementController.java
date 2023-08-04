package com.fruit.mall.admin.product;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductManagementController {
    private final ProductService productService;

    @GetMapping("/admin/product")
    public String paging(@Login SessionUser admin, Model model,
                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        PageInfo<Product> pageInfo = productService.getProducts(pageNum, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/product";
    }
}
