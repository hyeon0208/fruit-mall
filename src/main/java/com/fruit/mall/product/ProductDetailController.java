package com.fruit.mall.product;

import com.fruit.mall.cart.CartService;
import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.like.LikeService;
import com.fruit.mall.product.dto.ProductDetailForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.fruit.mall.user.MainController.recentProducts;

@Controller
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductService productService;
    private final LikeService likeService;
    private final CartService cartService;

    @GetMapping("/user/detail/{productId}")
    public String showDetail(@Login SessionUser sessionUser, @PathVariable("productId") Long productId, Model model) {
        ProductDetailForm productDetailForm = productService.selectProductDetailByProductId(productId, sessionUser);
        model.addAttribute("productDetailForm", productDetailForm);
        model.addAttribute("recentProducts", recentProducts);
        model.addAttribute("productId", productId);

        if (sessionUser != null) {
            int likesCount = likeService.countLikesByUserId(sessionUser.getUserIdNo());
            model.addAttribute("likesCount", likesCount);

            int userCartsCount = cartService.countCartByUserId(sessionUser.getUserIdNo());
            model.addAttribute("userCartsCount", userCartsCount);
        }

        return "user/detail";
    }
}
