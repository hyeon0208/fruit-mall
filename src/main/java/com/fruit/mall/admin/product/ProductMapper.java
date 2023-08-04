package com.fruit.mall.admin.product;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    void insertProduct(Product product);

    Product selectProductAllById(@Param("productId") Long id);

    List<Product> selectAll();
}
