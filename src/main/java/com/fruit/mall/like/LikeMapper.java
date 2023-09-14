package com.fruit.mall.like;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
    void insertLike(Like like);

    void deleteLike(@Param("userIdNo") Long userIdNo, @Param("productId") Long id);

    int countLikesByUserId(@Param("userIdNo") Long id);
}
