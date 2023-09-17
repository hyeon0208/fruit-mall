package com.fruit.mall.cart;

import com.fruit.mall.cart.dto.CartAndImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartRepository implements CartMapper {
    private final CartMapper cartMapper;

    @Override
    public void newUserCart(Long userIdNo) {
        cartMapper.newUserCart(userIdNo);
    }

    @Override
    public Long selectUserCartId(Long userIdNo) {
        return cartMapper.selectUserCartId(userIdNo);
    }

    @Override
    public void addProductToCart(CartProduct cart) {
        cartMapper.addProductToCart(cart);
    }

    @Override
    public Optional<CartProduct> selectCartProductByProductId(Long userIdNo, Long productId) {
        return cartMapper.selectCartProductByProductId(userIdNo, productId);
    }

    @Override
    public void deleteProductToCart(Long cartId, Long productId) {
        cartMapper.deleteProductToCart(cartId, productId);
    }

    @Override
    public List<CartAndImageDto> selectCartAndImageByUserId(Long userIdNo) {
        return cartMapper.selectCartAndImageByUserId(userIdNo);
    }

    @Override
    public void updateProductCnt(int productCount, Long cartId, Long productId) {
        cartMapper.updateProductCnt(productCount, cartId, productId);
    }

    @Override
    public int countCartByUserId(Long userIdNo) {
        return cartMapper.countCartByUserId(userIdNo);
    }
}
