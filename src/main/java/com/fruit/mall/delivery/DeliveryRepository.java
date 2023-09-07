package com.fruit.mall.delivery;

import com.fruit.mall.delivery.dto.DeliveryResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Optional<DeliveryResDto> selectOneByUserIdAndDeliveryName(Long userIdNo, String deliveryName) {
        return deliveryMapper.selectOneByUserIdAndDeliveryName(userIdNo, deliveryName);
    }

    @Override
    public void deleteDelivery(String deliveryName, Long userIdNo) {
        deliveryMapper.deleteDelivery(deliveryName, userIdNo);
    }
}
