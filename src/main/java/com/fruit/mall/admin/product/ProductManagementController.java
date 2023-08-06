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

    @GetMapping("/product")
    public String paging(@Login SessionUser admin, Model model,
                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        PageInfo<Product> pageInfo = productService.getProducts(pageNum, pageSize);

        int onSaleCount = productService.countOnSaleProducts();
        int offSaleCount = productService.countOffSaleProducts();
        int soldOutCount = productService.countSoldOutProducts();

        model.addAttribute("onSaleCount", onSaleCount);
        model.addAttribute("offSaleCount", offSaleCount);
        model.addAttribute("soldOutCount", soldOutCount);

        model.addAttribute("pageInfo", pageInfo);
        return "admin/product";
    }

    @PostMapping("/salestop")
    @ResponseBody
    public String clickSaleStopBtn(@RequestBody SaleStopDto saleStopDTO) {
        System.out.println("받은 데이터 : " + saleStopDTO.getProductId() + " | " + saleStopDTO.getStatus());
        productService.updateProductStatus(saleStopDTO.getProductId(), saleStopDTO.getStatus());
        Product updatedProduct = productService.selectProductAllById(saleStopDTO.getProductId());

        System.out.println("업데이트 상품 : " + updatedProduct);
        String updatedTime = String.valueOf(updatedProduct.getProductUpdatedAt());

        return updatedTime;
    }
}
