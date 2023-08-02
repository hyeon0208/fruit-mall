package com.fruit.mall.admin.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements ProductMapper {
    private final ProductMapper productMapper;

    @Override
    public void insertProduct(Long categoryId, String productName, int productPrice, int productStock, String productDescription, int productDiscount) {
        productMapper.insertProduct(categoryId, productName, productPrice, productStock, productDescription, productDiscount);
    }
}
