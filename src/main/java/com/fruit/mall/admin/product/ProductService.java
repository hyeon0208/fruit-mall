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

    public PageInfo<Product> getProducts(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, "PRODUCT_ID DESC");
        List<Product> products = selectAll();
        return new PageInfo<>(products);
    }

    public PageInfo<Product> getProductsByStatus(int pageNum, int pageSize, String status) {
        PageHelper.startPage(pageNum, pageSize, "PRODUCT_ID DESC");
        List<Product> products = selectAllByStatus(status);
        return new PageInfo<>(products);
    }

    public PageInfo<Product> getProductsByCategory(int pageNum, int pageSize, String category) {
        PageHelper.startPage(pageNum, pageSize, "PRODUCT_ID DESC");
        List<Product> products = selectAllByCategory(category);
        return new PageInfo<>(products);
    }

    public PageInfo<Product> getProductsByStatusAndCategory(int pageNum, int pageSize, String status, String category) {
        PageHelper.startPage(pageNum, pageSize, "PRODUCT_ID DESC");
        List<Product> products = selectAllByStatusAndCategory(status, category);
        return new PageInfo<>(products);
    }

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
