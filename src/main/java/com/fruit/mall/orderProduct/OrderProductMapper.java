package com.fruit.mall.orderProduct;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderProductMapper {
    void insertOrderProduct(OrderProduct orderProduct);
}
