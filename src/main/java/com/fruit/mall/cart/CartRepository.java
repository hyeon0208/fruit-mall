package com.fruit.mall.cart;

import com.fruit.mall.cart.dto.CartAndImageDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public void deleteProductToCart(Long cartId) {
        cartMapper.deleteProductToCart(cartId);
    }

    @Override
    public void deleteCartByUserIdAndProductId(Long userIdNo, Long productId) {
        cartMapper.deleteCartByUserIdAndProductId(userIdNo, productId);
    }

    @Override
    public List<CartAndImageDto> selectCartAndImageByUserId(Long userIdNo) {
        return cartMapper.selectCartAndImageByUserId(userIdNo);
    }

    @Override
    public void updateProductCnt(int productCount, Long cartId) {
        cartMapper.updateProductCnt(productCount, cartId);
    }

    @Override
    public int countCartByUserId(Long userIdNo) {
        return cartMapper.countCartByUserId(userIdNo);
    }
}
