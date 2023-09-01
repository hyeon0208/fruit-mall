package com.fruit.mall.cart;

import com.fruit.mall.cart.dto.*;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("/user/cart")
    public String loginUserCart(@Login SessionUser sessionUser, Model model) {
        if (sessionUser != null) {
            List<CartAndImageDto> cartAndImages = cartService.selectCartAndImageByUserId(sessionUser.getUserIdNo());
            model.addAttribute("cartAndImages", cartAndImages);
        }
        return "user/cart";
    }

    @PostMapping("/main/cart/add")
    @ResponseBody
    public Cart addProductToCart(@RequestBody CartAddReqDto cartAddReqDto) {
        Optional<Cart> findCart = cartService.selectByUserIdAndProductId(cartAddReqDto.getUserIdNo(), cartAddReqDto.getProductId());
        if (findCart.isPresent()) {
            if (findCart.get().getProductCount() != cartAddReqDto.getProductCount()) {
                cartService.updateProductCnt(cartAddReqDto.getProductCount(), findCart.get().getCartId());
                return findCart.get();
            } else {
                throw new IllegalArgumentException("이미 장바구니에 있는 상품입니다.");

            }
        }
        Cart cart = Cart.builder()
                .userIdNo(cartAddReqDto.getUserIdNo())
                .productId(cartAddReqDto.getProductId())
                .productCount(cartAddReqDto.getProductCount())
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

    @PostMapping("/user/update-cart")
    @ResponseBody
    public String mergeLocalStorageCart(@Login SessionUser sessionUser, @RequestBody LocalStorageDto localStorageDto) {
        if (localStorageDto.getLocalCarts() != null) {
            for (LocalCart localCart : localStorageDto.getLocalCarts()) {
                System.out.println("localCart = " + localCart);
                Cart lcart = Cart.builder()
                        .userIdNo(sessionUser.getUserIdNo())
                        .productId(localCart.getProduct().getProductId())
                        .productCount(localCart.getProduct().getProductCount())
                        .build();
                cartService.addProductToCart(lcart);
            }
        }
        return "success";
    }

    @PostMapping("/cart/increaseProductCnt")
    @ResponseBody
    public String increaseProductToCart(@RequestBody CartCntUpdateDto cartCntUpdateDto) {
        cartService.updateProductCnt(cartCntUpdateDto.getProductCount(), cartCntUpdateDto.getCartId());
        return "success";
    }

    @PostMapping("/cart/decreaseProductCnt")
    @ResponseBody
    public String decreaseProductToCart(@RequestBody CartCntUpdateDto cartCntUpdateDto) {
        cartService.updateProductCnt(cartCntUpdateDto.getProductCount(), cartCntUpdateDto.getCartId());
        return "success";
    }

    @DeleteMapping("/cart/delete/{cartId}")
    @ResponseBody
    public String deleteCart(@PathVariable Long cartId) {
        cartService.deleteProductToCart(cartId);
        return "/user/cart";
    }

    @DeleteMapping("/cart/delete/pay/success/{userIdNo}")
    @ResponseBody
    public String deleteCartByUserId(@PathVariable Long userIdNo) {
        cartService.deleteCartByUserId(userIdNo);
        return "success";
    }
}
