package com.fruit.mall.review;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {
    void insertReview(Review review);
}
