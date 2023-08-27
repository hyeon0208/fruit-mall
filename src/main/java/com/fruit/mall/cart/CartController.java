package com.fruit.mall.cart;

import com.fruit.mall.cart.dto.CartAddReqDto;
import com.fruit.mall.cart.dto.CartAndImageDto;
import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.product.ProductService;
import com.fruit.mall.product.dto.AddedProductToCartByNoLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("/user/cart/{userIdNo}")
    public String loginUserCart(@PathVariable Long userIdNo, Model model) {
        List<CartAndImageDto> cartAndImages = cartService.selectCartAndImageByUserId(userIdNo);
        model.addAttribute("cartAndImages", cartAndImages);
        return "user/cart";
    }

    @GetMapping("/user/cart")
    public String cart() {
        return "user/cart";
    }

    @PostMapping("/main/cart/add")
    @ResponseBody
    public Cart addProductToCart(@RequestBody CartAddReqDto cartAddReqDto) {
        Cart cart = Cart.builder()
                .userIdNo(cartAddReqDto.getUserIdNo())
                .productId(cartAddReqDto.getProductId())
                .build();

        Cart savedCart = cartService.addProductToCart(cart);
        return savedCart;
    }

    @GetMapping("/local/cart/{productId}")
    @ResponseBody
    public AddedProductToCartByNoLoginDto getProductInfo(@PathVariable Long productId) {
        AddedProductToCartByNoLoginDto product = productService.selectAddedProductByProductId(productId);
        product.setProductCount(1);
        return product;
    }

    @PostMapping("/main/cart/remove")
    @ResponseBody
    public String removeProductToCart(@RequestBody Cart cart) {

        return "success";
    }

    @PostMapping("/user/update-cart")
    @ResponseBody
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
