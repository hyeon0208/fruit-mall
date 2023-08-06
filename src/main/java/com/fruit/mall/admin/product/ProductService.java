package com.fruit.mall.admin.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements ProductMapper {
    private final ProductMapper productMapper;

    @Override
    public void insertProduct(Product product) {
        productMapper.insertProduct(product);
    }

    @Override
    public Product selectProductAllById(Long id) {
        return productMapper.selectProductAllById(id);
    }

    @Override
    public List<Product> selectAll() {
        return productMapper.selectAll();
    }

    public PageInfo<Product> getProducts(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = selectAll();
        return new PageInfo<>(products);
    }

    @Override
    public int countOnSaleProducts() {
        return productMapper.countOnSaleProducts();
    }

    @Override
    public int countOffSaleProducts() {
        return productMapper.countOffSaleProducts();
    }

    @Override
    public int countSoldOutProducts() {
        return productMapper.countSoldOutProducts();
    }

    @Override
    public void updateProductStatus(Long productId, String status) {
        productMapper.updateProductStatus(productId, status);
    }
}
