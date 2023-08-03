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
    public void insertProduct(Product product) {
        productMapper.insertProduct(product);
    }
}
