package com.fruit.mall.orders;

import com.fruit.mall.orders.dto.OrderReqDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderReqDto selectOneOrderInfoByProductId(@Param("productId") Long productId, @Param("productCount") int productCount);

    OrderReqDto selectOrderInfosByProductId(@Param("productId") Long productId);
}
