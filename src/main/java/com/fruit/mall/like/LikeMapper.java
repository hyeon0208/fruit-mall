package com.fruit.mall.like;

import com.google.common.math.LongMath;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LikeMapper {
    void insertLike(Like like);

    void deleteLike(@Param("productId") Long id);
}
