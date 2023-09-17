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
            cartService.updateCartTotalPrice(sessionUser.getCartId());
        }
        return "user/cart";
    }

    @PostMapping("/main/cart/add")
    @ResponseBody
    public void addProductToCart(@Login SessionUser sessionUser, @RequestBody CartAddReqDto cartAddReqDto) {
        Long cartId = sessionUser.getCartId();
        cartService.addProductToCart(sessionUser.getUserIdNo(), cartId, cartAddReqDto);
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

    @PostMapping("/cart/increaseProductCnt")
    @ResponseBody
    public String increaseProductToCart(@RequestBody CartCntUpdateDto cartCntUpdateDto) {
        int updateStock = cartCntUpdateDto.getProductCount();
        int curStock = productService.selectProductStock(cartCntUpdateDto.getProductId());
        cartService.updateProductCnt(cartCntUpdateDto.getProductCount(), cartCntUpdateDto.getCartId(), cartCntUpdateDto.getProductId());

        if ((curStock - updateStock) < 0) {
            return "재고부족";
        }
        return "success";
    }

    @PostMapping("/cart/decreaseProductCnt")
    @ResponseBody
    public String decreaseProductToCart(@RequestBody CartCntUpdateDto cartCntUpdateDto) {
        int curStock = productService.selectProductStock(cartCntUpdateDto.getProductId());
        int updateStock = cartCntUpdateDto.getProductCount();
        cartService.updateProductCnt(cartCntUpdateDto.getProductCount(), cartCntUpdateDto.getCartId(), cartCntUpdateDto.getProductId());

        if ((curStock - updateStock) < 0) {
            return "재고부족";
        }
        return "success";
    }

    @DeleteMapping("/cart/delete/{cartId}/{productId}")
    @ResponseBody
    public String deleteCart(@Login SessionUser sessionUser, @PathVariable Long cartId, @PathVariable Long productId) {
        cartService.deleteProductToCart(sessionUser.getUserIdNo(), cartId, productId);
        return "/user/cart";
    }

    @PostMapping("/cart/delete/pay/success")
    @ResponseBody
    public String deleteCartByUserId(@Login SessionUser sessionUser, @RequestBody List<Long> productIds) {
        cartService.deleteCartByPaymented(sessionUser.getCartId(), productIds);
        return "success";
    }
}
