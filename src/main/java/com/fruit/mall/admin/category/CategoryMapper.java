package com.fruit.mall.admin.category;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryMapper {
    void insertCategory(@Param("categoryName") String categoryName);
}
