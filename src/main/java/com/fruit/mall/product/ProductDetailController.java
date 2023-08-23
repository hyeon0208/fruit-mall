package com.fruit.mall.product;

import com.fruit.mall.product.dto.ProductDetailForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductService productService;

    @GetMapping("/user/detail/{productId}")
    public String showDetail(@PathVariable("productId") Long productId, Model model) {
        ProductDetailForm productDetailForm = productService.selectProductDetailByProductId(productId);

        System.out.println("폼 : " + productDetailForm);

        model.addAttribute("productDetailForm", productDetailForm);

        return "user/detail";
    }
}
