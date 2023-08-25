package com.fruit.mall.cart;

import com.fruit.mall.cart.dto.CartAddReqDto;
import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/main/cart/add")
    public Cart addProductToCart(@RequestBody CartAddReqDto cartAddReqDto) {
        Cart cart = Cart.builder()
                .userIdNo(cartAddReqDto.getUserIdNo())
                .productId(cartAddReqDto.getProductId())
                .build();

        Cart savedCart = cartService.addProductToCart(cart);
        return savedCart;
    }

    @PostMapping("/main/cart/remove")
    public String removeProductToCart(@RequestBody Cart cart) {

        return "success";
    }

    @PostMapping("/user/update-cart")
    public String mergeLocalStorageCart(@Login SessionUser sessionUser, @RequestBody Map<String, Object> localStorage) {
        List<Map<String, Object>> localCarts = (List<Map<String, Object>>) localStorage.get("localCart");
        if (!localCarts.isEmpty()) {
            for (Map<String, Object> localCart : localCarts) {
                Long productId = Long.valueOf((Integer) localCart.get("productId"));
                Cart lcart = Cart.builder()
                        .userIdNo(sessionUser.getUserIdNo())
                        .productId(productId)
                        .build();
                cartService.addProductToCart(lcart);
            }
        }
        return "success";
    }
}
