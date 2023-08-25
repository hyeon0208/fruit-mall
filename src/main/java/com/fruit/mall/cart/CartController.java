package com.fruit.mall.cart;

import com.fruit.mall.cart.dto.CartAddReqDto;
import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/main/cart/add")
    public List<Cart> addProductToCart(@Login SessionUser sessionUser, @RequestBody CartAddReqDto cartAddReqDto) {
        List<Cart> carts = new ArrayList<>();
        if (!cartAddReqDto.getLocalCart().isEmpty()) {
            List<Cart> localCarts = cartAddReqDto.getLocalCart();
            for (Cart localCart : localCarts) {
                Cart lcart = Cart.builder()
                        .userIdNo(sessionUser.getUserIdNo())
                        .productId(localCart.getProductId())
                        .build();
                Cart savedLCart = cartService.addProductToCart(lcart);
                carts.add(savedLCart);
            }
        }

        Cart cart = Cart.builder()
                .userIdNo(cartAddReqDto.getUserIdNo())
                .productId(cartAddReqDto.getProductId())
                .build();

        carts.add(cart);
        return carts;
    }

    @PostMapping("/main/cart/remove")
    public String removeProductToCart(@RequestBody Cart cart) {

        return "success";
    }
}
