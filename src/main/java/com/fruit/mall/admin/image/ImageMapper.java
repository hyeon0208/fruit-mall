package com.fruit.mall.admin.image;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ImageMapper {
    void insertImage(@Param("productId") Long productId,
                     @Param("imageUrl") String imageUrl,
                     @Param("imagePath") String imagePath,
                     @Param("imageFilename") String imageFilename);
}
