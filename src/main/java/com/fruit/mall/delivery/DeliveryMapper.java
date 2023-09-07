package com.fruit.mall.delivery;

import com.fruit.mall.delivery.dto.DeliveryResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DeliveryMapper {
    void insertDelivery(Delivery delivery);

    List<DeliveryResDto> deliveryAllByUserId(@Param("userIdNo") Long userIdNo);

    Optional<DeliveryResDto> selectOneByUserIdAndDeliveryName(@Param("userIdNo") Long userIdNo, @Param("deliveryName") String deliveryName);

    void deleteDelivery(@Param("deliveryName") String deliveryName, @Param("userIdNo") Long userIdNo);
}
