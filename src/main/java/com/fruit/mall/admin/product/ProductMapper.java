package com.fruit.mall.admin.product;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {

    void insertProduct(Product product);

    Product selectAllById(@Param("productId") Long id);
}
