package com.fruit.mall.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryRepository implements DeliveryMapper {
    private final DeliveryMapper deliveryMapper;

    @Override
    public void insertDelivery(Delivery delivery) {
        deliveryMapper.insertDelivery(delivery);
    }
}
