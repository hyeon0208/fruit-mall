package com.fruit.mall.admin.product;

import com.fruit.mall.admin.product.dto.ProductAndImageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    void insertProduct(Product product);

    void updateProduct(Product product);

    void deleteProductById(@Param("productId") Long id);

    Product selectProductAllById(@Param("productId") Long id);

    List<ProductAndImageInfo> selectProductAndImageByFilter(@Param("category") String category, @Param("searchCond") String searchCond);

    List<Product> selectAll();

    List<Product> selectAllByFilter(@Param("status") String status, @Param("category") String category, @Param("searchCond") String searchCond);

    int countTotalProducts();

    int countOnSaleProducts();

    int countOffSaleProducts();

    int countSoldOutProducts();

    void updateProductStatus(@Param("productId") Long productId, @Param("status") String status);
}
