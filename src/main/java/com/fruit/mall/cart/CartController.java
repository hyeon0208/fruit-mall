package com.fruit.mall.cart;

import com.fruit.mall.cart.dto.CartAddReqDto;
import com.fruit.mall.cart.dto.CartAndImageDto;
import com.fruit.mall.cart.dto.CartCntUpdateDto;
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
            throw new IllegalArgumentException("이미 장바구니에 있는 상품입니다.");
        }

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
                Map<String, Object> productMap = (Map<String, Object>) localCart.get("product");
                Long productId = Long.valueOf((Integer) productMap.get("productId"));
                int productCount = (int) localCart.get("quantity");
                Cart lcart = Cart.builder()
                        .userIdNo(sessionUser.getUserIdNo())
                        .productId(productId)
                        .productCount(productCount)
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
}
