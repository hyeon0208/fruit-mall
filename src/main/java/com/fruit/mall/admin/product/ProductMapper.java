package com.fruit.mall.admin.product;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {

    void insertProduct(@Param("categoryId") Long categoryId,
                       @Param("productName") String productName,
                       @Param("productPrice") int productPrice,
                       @Param("productStock") int productStock,
                       @Param("productDescription") String productDescription,
                       @Param("productDiscount") int productDiscount);
}
