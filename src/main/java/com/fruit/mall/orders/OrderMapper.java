package com.fruit.mall.orders;

import com.fruit.mall.orders.dto.OrderReqDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<OrderReqDto> selectOneOrderInfoByProductId(@Param("productId") Long productId);
}
