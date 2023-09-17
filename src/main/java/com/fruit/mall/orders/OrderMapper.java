package com.fruit.mall.orders;

import com.fruit.mall.orders.dto.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    void insertOrder(Orders orders);

    OrderInfo selectOneOrderInfoByProductId(@Param("productId") Long productId, @Param("productCount") int productCount, @Param("userIdNo") Long userIdNo);

    OrderInfo selectOrderInfosByProductId(@Param("productId") Long productId, @Param("userIdNo") Long userIdNo);
}
