package com.fruit.mall.product;

import com.fruit.mall.product.dto.AddedProductToCartByNoLoginDto;
import com.fruit.mall.product.dto.ProductAndImageInfo;
import com.fruit.mall.product.dto.ProductDetailForm;
import com.fruit.mall.product.dto.ProductPriceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository implements ProductMapper {
    private final ProductMapper productMapper;

    @Override
    public void updateProductStock(Long id, int orderCount) {
        productMapper.updateProductStock(id, orderCount);
    }

    @Override
    public int selectProductStock(Long id) {
        return productMapper.selectProductStock(id);
    }

    @Override
    public Product selectProductAllById(Long id) {
        return productMapper.selectProductAllById(id);
    }

    @Override
    public List<Product> selectAll() {
        return productMapper.selectAll();
    }

    @Override
    public List<ProductAndImageInfo> selectProductAndImageByFilter(String category, String searchCond, Long id) {
        return productMapper.selectProductAndImageByFilter(category, searchCond, id);
    }

    @Override
    public ProductDetailForm selectProductDetailByProductId(Long id, Long userIdNo) {
        return productMapper.selectProductDetailByProductId(id, userIdNo);
    }

    @Override
    public AddedProductToCartByNoLoginDto selectAddedProductByProductId(Long id) {
        return productMapper.selectAddedProductByProductId(id);
    }

    @Override
    public ProductPriceInfo selectPriceAndDiscountById(Long id) {
        return productMapper.selectPriceAndDiscountById(id);
    }
}
