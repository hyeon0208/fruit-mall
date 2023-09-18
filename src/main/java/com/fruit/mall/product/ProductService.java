package com.fruit.mall.product;

import com.fruit.mall.product.dto.AddedProductToCartByNoLoginDto;
import com.fruit.mall.product.dto.ProductAndImageInfo;
import com.fruit.mall.product.dto.ProductDetailForm;
import com.fruit.mall.product.dto.ProductPriceInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public PageInfo<ProductAndImageInfo> getProductsAndImageByFilter(int pageNum, int pageSize, String category, String searchCond, Long userId) {
        PageHelper.startPage(pageNum, pageSize, "PRODUCT_ID DESC");
        List<ProductAndImageInfo> productAndImageInfos = productRepository.selectProductAndImageByFilter(category, searchCond, userId);
        return new PageInfo<>(productAndImageInfos);
    }

    public int selectProductStock(Long id) {
        return productRepository.selectProductStock(id);
    }

    public Product selectProductAllById(Long id) {
        return productRepository.selectProductAllById(id);
    }


    public ProductDetailForm selectProductDetailByProductId(Long productId, Long userId) {
        return productRepository.selectProductDetailByProductId(productId, userId);
    }

    public AddedProductToCartByNoLoginDto selectAddedProductByProductId(Long id) {
        return productRepository.selectAddedProductByProductId(id);
    }

    public ProductPriceInfo selectPriceAndDiscountById(Long id) {
        return productRepository.selectPriceAndDiscountById(id);
    }
}
