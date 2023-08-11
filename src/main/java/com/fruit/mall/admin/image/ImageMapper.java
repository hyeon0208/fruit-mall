package com.fruit.mall.admin.image;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageMapper {
    void insertImage(Image image);

    void updateImage(Image image);

    void deleteImagesByProductId(@Param("productId") Long id);

    String selectProductImageUrlByProductId(@Param("productId") Long id);

    List<Image> selectImagesByProductId(@Param("productId") Long id);
}
