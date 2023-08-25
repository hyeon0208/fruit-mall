package com.fruit.mall.cart;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface CartMapper {
    Long addProductToCart(Cart cart);

    Optional<Cart> selectByUserIdAndProductId(@Param("userIdNo") Long userIdNo, @Param("productId") Long productId);

    Cart selectByCartId(@Param("cartId") Long cartId);

    void deleteProductToCart(@Param("productId") Long productId);

    int countCartByUserId(@Param("userIdNo") Long userIdNo);

}
