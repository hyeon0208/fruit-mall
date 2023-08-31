package com.fruit.mall.delivery;

import com.fruit.mall.delivery.dto.DeliveryResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeliveryRepository implements DeliveryMapper {
    private final DeliveryMapper deliveryMapper;

    @Override
    public void insertDelivery(Delivery delivery) {
        deliveryMapper.insertDelivery(delivery);
    }

    @Override
    public List<DeliveryResDto> deliveryAllByUserId(Long userIdNo) {
        return deliveryMapper.deliveryAllByUserId(userIdNo);
    }

    @Override
    public DeliveryResDto selectOneByUserIdAndDeliveryName(Long userIdNo, String deliveryName) {
        return deliveryMapper.selectOneByUserIdAndDeliveryName(userIdNo, deliveryName);
    }
}
