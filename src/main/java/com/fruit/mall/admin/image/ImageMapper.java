package com.fruit.mall.admin.image;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageMapper {
    void insertImage(Image image);

    Image selectAllById(@Param("imageId") Long id);

    List<String> selectImageUrlByProductId(@Param("productId") Long id);
}
