package com.fruit.mall.delivery;

import com.fruit.mall.delivery.dto.DeliveryResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeliveryMapper {
    void insertDelivery(Delivery delivery);

    List<DeliveryResDto> deliveryAllByUserId(@Param("userIdNo") Long userIdNo);

    DeliveryResDto selectOneByUserIdAndDeliveryName(@Param("userIdNo") Long userIdNo, @Param("deliveryName") String deliveryName);
}
