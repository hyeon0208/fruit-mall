package com.fruit.mall.admin.product;

import com.fruit.mall.admin.product.dto.PageResDto;
import com.fruit.mall.admin.product.dto.SaleStopDto;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ProductManagementController {
    private final ProductService productService;

    @GetMapping("/product")
    public String paging(Model model,
                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        PageInfo<Product> pageInfo = productService.getProducts(pageNum, pageSize);

        int totalCount = productService.countTotalProducts();
        int onSaleCount = productService.countOnSaleProducts();
        int offSaleCount = productService.countOffSaleProducts();
        int soldOutCount = productService.countSoldOutProducts();

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("onSaleCount", onSaleCount);
        model.addAttribute("offSaleCount", offSaleCount);
        model.addAttribute("soldOutCount", soldOutCount);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/product";
    }

    @GetMapping("/searchfilter")
    @ResponseBody
    public PageResDto searchFilter(
            @RequestParam HashMap<String, String> params,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        String status = params.get("status");
        String category = params.get("category");
        String searchCond = params.get("searchCond");

        System.out.println("category : " + category);
        PageInfo<Product> pageInfo = productService.getProductsByFilter(pageNum, pageSize, status, category, searchCond);

        PageResDto pageResDto = PageResDto.builder()
                .pageInfo(pageInfo)
                .status(status)
                .category(category)
                .build();

        return pageResDto;
    }

    @PostMapping("/salestop")
    @ResponseBody
    public String clickSaleStopBtn(@RequestBody SaleStopDto saleStopDTO) {
        productService.updateProductStatus(saleStopDTO.getProductId(), saleStopDTO.getStatus());
        Product updatedProduct = productService.selectProductAllById(saleStopDTO.getProductId());
        String updatedTime = String.valueOf(updatedProduct.getProductUpdatedAt());

        return updatedTime;
    }

    @GetMapping("/edit/product/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.selectProductAllById(productId);
        model.addAttribute("product", product);
        return "admin/editForm";
    }
}
