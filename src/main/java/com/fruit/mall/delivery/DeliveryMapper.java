package com.fruit.mall.delivery;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryMapper {
    void insertDelivery(Delivery delivery);

}
