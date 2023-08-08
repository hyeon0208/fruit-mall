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
public class ProductService {
    private final ProductRepository productRepository;

    public PageInfo<Product> getProducts(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, "PRODUCT_ID DESC");
        List<Product> products = productRepository.selectAll();
        return new PageInfo<>(products);
    }

    public PageInfo<Product> getProductsByFilter(int pageNum, int pageSize, String status, String category, String searchCond) {
        PageHelper.startPage(pageNum, pageSize, "PRODUCT_ID DESC");
        List<Product> products = productRepository.selectAllByFilter(status, category, searchCond);
        return new PageInfo<>(products);
    }

    public void insertProduct(Product product) {
        productRepository.insertProduct(product);
    }

    public Product selectProductAllById(Long id) {
        return productRepository.selectProductAllById(id);
    }


    public int countTotalProducts() {
        return productRepository.countTotalProducts();
    }

    public int countOnSaleProducts() {
        return productRepository.countOnSaleProducts();
    }

    public int countOffSaleProducts() {
        return productRepository.countOffSaleProducts();
    }

    public int countSoldOutProducts() {
        return productRepository.countSoldOutProducts();
    }

    public void updateProductStatus(Long productId, String status) {
        productRepository.updateProductStatus(productId, status);
    }
}
