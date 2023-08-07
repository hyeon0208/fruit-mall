package com.fruit.mall.admin.product;

import com.fruit.mall.admin.product.dto.SaleStopDto;
import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ProductManagementController {
    private final ProductService productService;
    private final static String ALL_STATUS = "전체상태";
    private final static String ALL_CATEGORY = "전체카테고리";
    private final static String NO_SEARCH = "no";

    @GetMapping("/product")
    public String paging(Model model,
                         @RequestParam(value = "status", defaultValue = ALL_STATUS) String status,
                         @RequestParam(value = "category", defaultValue = ALL_CATEGORY) String category,
                         @RequestParam(value = "searchCond", defaultValue = NO_SEARCH) String searchCond,
                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        PageInfo<Product> pageInfo;

        if (status.equals(ALL_STATUS) && category.equals(ALL_CATEGORY)) {
            pageInfo = productService.getProducts(pageNum, pageSize);
        } else if (!status.equals(ALL_STATUS) && category.equals(ALL_CATEGORY)) {
            pageInfo = productService.getProductsByStatus(pageNum, pageSize, status);
        } else if (status.equals(ALL_STATUS) && !category.equals(ALL_CATEGORY)) {
            pageInfo = productService.getProductsByCategory(pageNum, pageSize, category);
        } else {
            pageInfo = productService.getProductsByStatusAndCategory(pageNum, pageSize, status, category);
        }

        if (!searchCond.equals(NO_SEARCH)) {
            pageInfo = productService.getProductsBySearchCond(pageNum, pageSize, searchCond);
        }

        int totalCount = productService.countTotalProducts();
        int onSaleCount = productService.countOnSaleProducts();
        int offSaleCount = productService.countOffSaleProducts();
        int soldOutCount = productService.countSoldOutProducts();

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("onSaleCount", onSaleCount);
        model.addAttribute("offSaleCount", offSaleCount);
        model.addAttribute("soldOutCount", soldOutCount);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("saleStatus", status);
        model.addAttribute("category", category);
        return "admin/product";
    }

    @PostMapping("/salestop")
    @ResponseBody
    public String clickSaleStopBtn(@RequestBody SaleStopDto saleStopDTO) {
        productService.updateProductStatus(saleStopDTO.getProductId(), saleStopDTO.getStatus());
        Product updatedProduct = productService.selectProductAllById(saleStopDTO.getProductId());
        String updatedTime = String.valueOf(updatedProduct.getProductUpdatedAt());

        return updatedTime;
    }
}
