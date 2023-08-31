package com.fruit.mall.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public void insertDelivery(Long userId, Delivery delivery) {
        Delivery newDelivery = Delivery.builder()
                .userIdNo(userId)
                .deliveryName(delivery.getDeliveryName())
                .userName(delivery.getUserName())
                .phoneNumber(delivery.getPhoneNumber())
                .zipcode(delivery.getZipcode())
                .address(delivery.getAddress())
                .build();
        deliveryRepository.insertDelivery(newDelivery);
    }
}
