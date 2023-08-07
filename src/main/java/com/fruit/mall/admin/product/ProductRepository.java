package com.fruit.mall.admin.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository implements ProductMapper {
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

    @Override
    public List<Product> selectAllByStatus(String status) {
        return productMapper.selectAllByStatus(status);
    }

    @Override
    public List<Product> selectAllByCategory(String category) {
        return productMapper.selectAllByCategory(category);
    }

    @Override
    public List<Product> selectAllByStatusAndCategory(String status, String category) {
        return productMapper.selectAllByStatusAndCategory(status, category);
    }

    @Override
    public List<Product> selectAllBySearchCond(String searchCond) {
        return productMapper.selectAllBySearchCond(searchCond);
    }

    @Override
    public int countTotalProducts() {
        return productMapper.countTotalProducts();
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
