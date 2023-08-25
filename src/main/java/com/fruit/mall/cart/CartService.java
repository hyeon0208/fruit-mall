package com.fruit.mall.cart;

import com.fruit.mall.product.Product;
import com.fruit.mall.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public Cart addProductToCart(Cart cart) {
        Optional<Cart> findCart = cartRepository.selectByUserIdAndProductId(cart.getUserIdNo(), cart.getProductId());

        if (findCart.isPresent()) {
            return findCart.get();
        }

        Product product = productService.selectProductAllById(cart.getProductId());
        Cart insertCart = Cart.builder()
                .userIdNo(cart.getUserIdNo())
                .productId(cart.getProductId())
                .productCount(1)
                .productPrice(product.getProductPrice())
                .productDiscount(product.getProductDiscount())
                .productSaleStatus(product.getProductSaleStatus())
        .build();

        Long cartId = cartRepository.addProductToCart(insertCart);
        return cartRepository.selectByCartId(cartId);
    }

    public Optional<Cart> selectByUserIdAndProductId(Long userIdNo, Long productId) {
       return cartRepository.selectByUserIdAndProductId(userIdNo, productId);
    }

    public Cart selectByCartId(Long cartId) {
        return cartRepository.selectByCartId(cartId);
    }

    public void deleteProductToCart(Long productId) {
        cartRepository.deleteProductToCart(productId);
    }

    public int countCartByUserId(Long userIdNo) {
        return cartRepository.countCartByUserId(userIdNo);
    }
}
