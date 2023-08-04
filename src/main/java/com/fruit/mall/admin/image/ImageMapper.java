package com.fruit.mall.admin.image;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ImageMapper {
    void insertImage(Image image);

    Image selectAllById(@Param("imageId") Long id);
}
