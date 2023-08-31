package com.fruit.mall.delivery;

import com.fruit.mall.delivery.dto.DeliveryResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<DeliveryResDto> deliveryAllByUserId(Long userIdNo) {
        return deliveryRepository.deliveryAllByUserId(userIdNo);
    }
}
