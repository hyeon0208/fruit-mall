package com.fruit.mall.cart;

import com.fruit.mall.cart.dto.CartAndImageDto;
import com.fruit.mall.product.Product;
import com.fruit.mall.product.ProductService;
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
    private final ProductService productService;

    @CacheEvict(value = CART_COUNT, key = "#cart.userIdNo", cacheManager = "cacheManager")
    public Cart addProductToCart(Cart cart) {
        Optional<Cart> findCart = cartRepository.selectByUserIdAndProductId(cart.getUserIdNo(), cart.getProductId());
        if (findCart.isPresent()) {
            if (findCart.get().getProductCount() != cart.getProductCount()) {
                cartRepository.updateProductCnt(cart.getProductCount(), findCart.get().getCartId());
            }
            return findCart.get();
        }

        Product product = productService.selectProductAllById(cart.getProductId());
        Cart insertCart = Cart.builder()
                .userIdNo(cart.getUserIdNo())
                .productId(cart.getProductId())
                .productCount(cart.getProductCount())
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

    @CacheEvict(value = CART_COUNT, key = "#userId", cacheManager = "cacheManager")
    public void deleteProductToCart(Long userId, Long cartId) {
        cartRepository.deleteProductToCart(cartId);
    }

    @CacheEvict(value = CART_COUNT, key = "#userIdNo", cacheManager = "cacheManager")
    public void deleteCartByUserIdAndProductId(Long userIdNo, List<Long> productIds) {
        for (Long productId : productIds) {
            cartRepository.deleteCartByUserIdAndProductId(userIdNo, productId);
        }
    }

    public List<CartAndImageDto> selectCartAndImageByUserId(Long userIdNo) {
        return cartRepository.selectCartAndImageByUserId(userIdNo);
    }

    public void updateProductCnt(int productCount, Long cartId) {
        cartRepository.updateProductCnt(productCount, cartId);
    }

    @Cacheable(value = CART_COUNT, key = "#userIdNo", condition = "#userIdNo != null", cacheManager = "cacheManager")
    public int countCartByUserId(Long userIdNo) {
        return cartRepository.countCartByUserId(userIdNo);
    }
}
