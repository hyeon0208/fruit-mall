package com.fruit.mall.product;

import com.fruit.mall.annotaion.Login;
import com.fruit.mall.config.SessionUser;
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
    public String showDetail(@Login SessionUser sessionUser, Model model, @PathVariable("productId") Long productId) {
        Long userId = sessionUser != null ? sessionUser.getUserIdNo() : null;
        ProductDetailForm productDetailForm = productService.selectProductDetailByProductId(productId, userId);
        model.addAttribute("productDetailForm", productDetailForm);
        model.addAttribute("productId", productId);

        return "user/detail";
    }
}
