package com.fruit.mall.product;

import com.fruit.mall.product.dto.AddedProductToCartByNoLoginDto;
import com.fruit.mall.product.dto.ProductAndImageInfo;
import com.fruit.mall.product.dto.ProductDetailForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

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

    public PageInfo<ProductAndImageInfo> getProductsAndImageByFilter(int pageNum, int pageSize, String category, String searchCond, Long userId) {
        PageHelper.startPage(pageNum, pageSize, "PRODUCT_ID DESC");
        List<ProductAndImageInfo> productAndImageInfos = productRepository.selectProductAndImageByFilter(category, searchCond, userId);
        return new PageInfo<>(productAndImageInfos);
    }


    public String getUpdatedDescription(String description, String editorFirebaseImageUrl, String blobUrl) {
        String patternString = "<img([^>]*)src=[\"']" + Pattern.quote(blobUrl) + "[\"']([^>]*)>";
        Pattern pattern = Pattern.compile(patternString);
        String updatedDiscription = pattern.matcher(description).replaceAll(String.format("<img src=\"%s\"$1$2", editorFirebaseImageUrl));
        return updatedDiscription;
    }

    public void insertProduct(Product product) {
        productRepository.insertProduct(product);
    }

    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteProductById(id);
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

    public ProductDetailForm selectProductDetailByProductId(Long productId, Long userId) {
        return productRepository.selectProductDetailByProductId(productId, userId);
    }

    public AddedProductToCartByNoLoginDto selectAddedProductByProductId(Long id) {
        return productRepository.selectAddedProductByProductId(id);
    }

    public void updateProductStatus(Long productId, String status) {
        productRepository.updateProductStatus(productId, status);
    }
}
