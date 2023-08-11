package com.fruit.mall.admin.product;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    void insertProduct(Product product);

    void updateProduct(Product product);

    Product selectProductAllById(@Param("productId") Long id);

    List<Product> selectAll();

    List<Product> selectAllByFilter(@Param("status") String status, @Param("category") String category, @Param("searchCond") String searchCond);

    int countTotalProducts();

    int countOnSaleProducts();

    int countOffSaleProducts();

    int countSoldOutProducts();

    void updateProductStatus(@Param("productId") Long productId, @Param("status") String status);
}
