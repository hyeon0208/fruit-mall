package com.fruit.mall.cart;

import com.fruit.mall.cart.dto.CartAndImageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CartMapper {
    void newUserCart(@Param("userIdNo") Long userIdNo);

    Long selectUserCartId(@Param("userIdNo") Long userIdNo);

    void addProductToCart(CartProduct cart);

    Optional<CartProduct> selectCartProductByProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);

    void deleteProductToCart(@Param("cartId") Long cartId, @Param("productId") Long productId);

    List<CartAndImageDto> selectCartAndImageByUserId(@Param("userIdNo") Long userIdNo);

    int countCartByUserId(@Param("userIdNo") Long userIdNo);

    void updateProductCnt(@Param("productCount") int productCount, @Param("cartId") Long cartId, @Param("productId") Long productId);

    void updateCartTotalPrice(@Param("cartId") Long cartId);
}
