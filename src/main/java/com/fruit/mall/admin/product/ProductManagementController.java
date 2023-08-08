package com.fruit.mall.admin.product;

import com.fruit.mall.admin.product.dto.SaleStopDto;
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
    public PageInfo<Product> searchFilter(
            @RequestParam(value = "status",  required = false) String status,
            @RequestParam(value = "category",  required = false) String category,
            @RequestParam(value = "searchCond",  required = false) String searchCond,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        PageInfo<Product> pageInfo = productService.getProductsByFilter(pageNum, pageSize, status, category, searchCond);
        return pageInfo;
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
