package com.fruit.mall.product;

import com.fruit.mall.product.dto.AddedProductToCartByNoLoginDto;
import com.fruit.mall.product.dto.ProductAndImageInfo;
import com.fruit.mall.product.dto.ProductDetailForm;
import com.fruit.mall.product.dto.ProductPriceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    void updateProductStock(@Param("productId") Long id, @Param("orderCount") int orderCount);

    int selectProductStock(@Param("productId") Long id);

    ProductDetailForm selectProductDetailByProductId(@Param("productId") Long id, @Param("userIdNo") Long userIdNo);

    Product selectProductAllById(@Param("productId") Long id);

    AddedProductToCartByNoLoginDto selectAddedProductByProductId(@Param("productId") Long id);

    List<ProductAndImageInfo> selectProductAndImageByFilter(@Param("category") String category, @Param("searchCond") String searchCond, @Param("userIdNo") Long userIdNo);

    List<Product> selectAll();

    ProductPriceInfo selectPriceAndDiscountById(@Param("productId") Long id);
}
