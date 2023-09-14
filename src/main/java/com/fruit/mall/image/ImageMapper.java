package com.fruit.mall.image;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageMapper {
    List<String> selectFileNamesByProductId(@Param("productId") Long id);

    String selectProductImageUrlByProductId(@Param("productId") Long id);

    List<Image> selectImagesByProductId(@Param("productId") Long id);
}
