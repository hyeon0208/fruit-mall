package com.fruit.mall.cart;

import com.fruit.mall.cart.dto.*;
import com.fruit.mall.annotaion.Login;
import com.fruit.mall.config.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/user/cart")
    public String loginUserCart(@Login SessionUser sessionUser, Model model) {
        if (sessionUser != null) {
            List<CartAndImageDto> cartAndImages = cartService.selectCartAndImageByUserId(sessionUser.getUserIdNo());
            model.addAttribute("cartAndImages", cartAndImages);
            if (cartAndImages.size() > 0) {
                cartService.updateCartTotalPrice(sessionUser.getCartId());
            }
        }
        return "user/cart";
    }
}
