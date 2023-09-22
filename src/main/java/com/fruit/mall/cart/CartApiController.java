package com.fruit.mall.cart;

import com.fruit.mall.annotaion.Login;
import com.fruit.mall.cart.dto.CartAddReqDto;
import com.fruit.mall.cart.dto.CartCntUpdateDto;
import com.fruit.mall.cart.dto.LocalCart;
import com.fruit.mall.cart.dto.LocalStorageDto;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.product.ProductService;
import com.fruit.mall.product.dto.AddedProductToCartByNoLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CartApiController {
    private final CartService cartService;
    private final ProductService productService;

    @PostMapping("/cart")
    public void addProductToCart(@Login SessionUser sessionUser, @RequestBody CartAddReqDto cartAddReqDto) {
        Long cartId = sessionUser.getCartId();
        cartService.addProductToCart(sessionUser.getUserIdNo(), cartId, cartAddReqDto);
    }

    @GetMapping("/local/cart/{productId}")
    public AddedProductToCartByNoLoginDto getProductInfo(@PathVariable Long productId) {
        AddedProductToCartByNoLoginDto product = productService.selectAddedProductByProductId(productId);
        product.setProductCount(1);
        return product;
    }

    @PostMapping("/cart/merge")
    public String mergeLocalStorageCart(@Login SessionUser sessionUser, @RequestBody LocalStorageDto localStorageDto) {
        if (localStorageDto.getLocalCarts() != null) {
            for (LocalCart localCart : localStorageDto.getLocalCarts()) {
                CartAddReqDto dto = CartAddReqDto.builder()
                        .productId(localCart.getProduct().getProductId())
                        .productPrice(localCart.getProduct().getProductPrice())
                        .productCount(localCart.getProduct().getProductCount())
                        .productDiscount(localCart.getProduct().getProductDiscount())
                        .build();
                cartService.addProductToCart(sessionUser.getUserIdNo(), sessionUser.getCartId(), dto);
            }
        }
        return "success";
    }

    @PatchMapping("/cart/count")
    public String updateCartProductCount(@RequestBody CartCntUpdateDto cartCntUpdateDto) {
        int updateStock = cartCntUpdateDto.getProductCount();
        int curStock = productService.selectProductStock(cartCntUpdateDto.getProductId());
        cartService.updateProductCnt(cartCntUpdateDto.getProductCount(), cartCntUpdateDto.getCartId(), cartCntUpdateDto.getProductId());

        if ((curStock - updateStock) < 0) {
            return "재고부족";
        }
        return "success";
    }

    @DeleteMapping("/cart/{cartId}/product/{productId}")
    public String deleteCart(@Login SessionUser sessionUser, @PathVariable Long cartId, @PathVariable Long productId) {
        cartService.deleteProductToCart(sessionUser.getUserIdNo(), cartId, productId);
        return "/user/cart";
    }

    @DeleteMapping("/cart/paymented")
    public String deleteCartByUserId(@Login SessionUser sessionUser, @RequestBody List<Long> productIds) {
        cartService.deleteCartByPaymented(sessionUser.getUserIdNo(), sessionUser.getCartId(), productIds);
        return "success";
    }
}
