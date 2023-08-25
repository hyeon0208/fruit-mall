package com.fruit.mall.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartRepository implements CartMapper {
    private final CartMapper cartMapper;

    @Override
    public Long addProductToCart(Cart cart) {
        return cartMapper.addProductToCart(cart);
    }

    @Override
    public Optional<Cart> selectByUserIdAndProductId(Long userIdNo, Long productId) {
        return cartMapper.selectByUserIdAndProductId(userIdNo, productId);
    }

    @Override
    public Cart selectByCartId(Long cartId) {
        return cartMapper.selectByCartId(cartId);
    }

    @Override
    public void deleteProductToCart(Long productId) {
        cartMapper.deleteProductToCart(productId);
    }

    @Override
    public int countCartByUserId(Long userIdNo) {
        return cartMapper.countCartByUserId(userIdNo);
    }
}
