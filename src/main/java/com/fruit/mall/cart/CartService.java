package com.fruit.mall.cart;

import com.fruit.mall.cart.dto.CartAddReqDto;
import com.fruit.mall.cart.dto.CartAndImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.fruit.mall.redis.RedisCacheKey.CART_COUNT;
import static com.fruit.mall.redis.RedisCacheKey.LIKE_COUNT;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    @CacheEvict(value = CART_COUNT, key = "#userId", cacheManager = "cacheManager")
    public void addProductToCart(Long userId, Long cartId, CartAddReqDto dto) {
        Optional<CartProduct> findCart = cartRepository.selectCartProductByProductId(cartId, dto.getProductId());
        if (findCart.isPresent()) {
            if (findCart.get().getProductCount() != dto.getProductCount()) {
                cartRepository.updateProductCnt(dto.getProductCount(), cartId, findCart.get().getProductId());
                return;
            } else {
                throw new IllegalArgumentException("이미 장바구니에 있는 상품입니다.");
            }
        }

        CartProduct cart = CartProduct.builder()
                .cartId(cartId)
                .productId(dto.getProductId())
                .productPrice(dto.getProductPrice())
                .productCount(dto.getProductCount())
                .productDiscount(dto.getProductDiscount())
                .build();
        cartRepository.addProductToCart(cart);
    }

    @CacheEvict(value = CART_COUNT, key = "#userId", cacheManager = "cacheManager")
    public void deleteProductToCart(Long userId, Long cartId, Long productId) {
        cartRepository.deleteProductToCart(cartId, productId);
    }

    @CacheEvict(value = CART_COUNT, key = "#userIdNo", cacheManager = "cacheManager")
    public void deleteCartByPaymented(Long cartId, List<Long> productIds) {
        for (Long productId : productIds) {
            cartRepository.deleteProductToCart(cartId, productId);
        }
    }

    public List<CartAndImageDto> selectCartAndImageByUserId(Long userIdNo) {
        return cartRepository.selectCartAndImageByUserId(userIdNo);
    }

    public void updateProductCnt(int productCount, Long cartId, Long productId) {
        cartRepository.updateProductCnt(productCount, cartId, productId);
    }

    public void updateCartTotalPrice(Long cartId) {
        cartRepository.updateCartTotalPrice(cartId);
    }

    @Cacheable(value = CART_COUNT, key = "#userIdNo", condition = "#userIdNo != null", cacheManager = "cacheManager")
    public int countCartByUserId(Long userIdNo) {
        return cartRepository.countCartByUserId(userIdNo);
    }
}
