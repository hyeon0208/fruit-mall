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
    public Cart addProductToCart(@Login SessionUser sessionUser, @RequestBody CartAddReqDto cartAddReqDto) {
        Long userId = sessionUser.getUserIdNo();
        Optional<Cart> findCart = cartService.selectByUserIdAndProductId(userId, cartAddReqDto.getProductId());
        if (findCart.isPresent()) {
            if (findCart.get().getProductCount() != cartAddReqDto.getProductCount()) {
                cartService.updateProductCnt(cartAddReqDto.getProductCount(), findCart.get().getCartId());
                return findCart.get();
            } else {
                throw new IllegalArgumentException("이미 장바구니에 있는 상품입니다.");
            }
        }
        Cart cart = Cart.builder()
                .userIdNo(userId)
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
        int updateStock = cartCntUpdateDto.getProductCount();
        int curStock = productService.selectProductStock(cartCntUpdateDto.getProductId());
        cartService.updateProductCnt(cartCntUpdateDto.getProductCount(), cartCntUpdateDto.getCartId());

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
        cartService.updateProductCnt(cartCntUpdateDto.getProductCount(), cartCntUpdateDto.getCartId());

        if ((curStock - updateStock) < 0) {
            return "재고부족";
        }
        return "success";
    }

    @DeleteMapping("/cart/delete/{cartId}")
    @ResponseBody
    public String deleteCart(@Login SessionUser sessionUser, @PathVariable Long cartId) {
        cartService.deleteProductToCart(sessionUser.getUserIdNo(), cartId);
        return "/user/cart";
    }

    @PostMapping("/cart/delete/pay/success")
    @ResponseBody
    public String deleteCartByUserId(@Login SessionUser sessionUser, @RequestBody List<Long> productIds) {
        cartService.deleteCartByUserIdAndProductId(sessionUser.getUserIdNo(), productIds);
        return "success";
    }
}
