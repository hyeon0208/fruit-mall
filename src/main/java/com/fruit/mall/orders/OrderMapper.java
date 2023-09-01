package com.fruit.mall.orders;

import com.fruit.mall.orders.dto.OrderReqDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    void insertOrder(Orders orders);

    OrderReqDto selectOneOrderInfoByProductId(@Param("productId") Long productId, @Param("productCount") int productCount);

    OrderReqDto selectOrderInfosByProductId(@Param("productId") Long productId);
}
