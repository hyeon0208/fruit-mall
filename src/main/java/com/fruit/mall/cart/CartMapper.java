package com.fruit.mall.cart;

import com.fruit.mall.cart.dto.CartAndImageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CartMapper {
    Long addProductToCart(Cart cart);

    Optional<Cart> selectByUserIdAndProductId(@Param("userIdNo") Long userIdNo, @Param("productId") Long productId);

    Cart selectByCartId(@Param("cartId") Long cartId);

    void deleteProductToCart(@Param("productId") Long productId);

    List<CartAndImageDto> selectCartAndImageByUserId(@Param("userIdNo") Long userIdNo);

    int countCartByUserId(@Param("userIdNo") Long userIdNo);

}
