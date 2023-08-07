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
    private final static String ALL_PRODUCT = "전체상품";

    @GetMapping("/product")
    public String paging(@Login SessionUser admin, Model model,
                                      @RequestParam(value = "status", defaultValue = ALL_PRODUCT) String status,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        PageInfo<Product> pageInfo;
        if (status.equalsIgnoreCase(ALL_PRODUCT)) {
            pageInfo = productService.getProducts(pageNum, pageSize);
        } else {
            pageInfo = productService.getOnSaleProducts(pageNum, pageSize, status);
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
