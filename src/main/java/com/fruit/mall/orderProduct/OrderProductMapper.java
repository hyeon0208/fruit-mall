package com.fruit.mall.orderProduct;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderProductMapper {
    void insertOrderProduct(OrderProduct orderProduct);

    List<Long> selectOPIdByOrderIdAndProductId(@Param("userIdNo") Long userIdNo, @Param("productId") Long productId);
}
