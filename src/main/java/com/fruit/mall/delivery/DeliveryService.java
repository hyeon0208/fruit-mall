package com.fruit.mall.delivery;

import com.fruit.mall.delivery.dto.DeliveryReqDto;
import com.fruit.mall.delivery.dto.DeliveryResDto;
import com.fruit.mall.delivery.dto.DeliveryUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public void insertDelivery(Long userId, DeliveryReqDto dto) {
        Optional<DeliveryResDto> findDelivery = deliveryRepository.selectOneByUserIdAndDeliveryName(userId, dto.getDeliveryName());

        if (findDelivery.isPresent()) {
            String findName = findDelivery.get().getDeliveryName();
            if (findName.equals(dto.getDeliveryName())) {
                throw new IllegalArgumentException("배송지 이름은 중복될수 없습니다.");
            }
        }

        Delivery newDelivery = Delivery.builder()
                .userIdNo(userId)
                .deliveryName(dto.getDeliveryName())
                .userName(dto.getUserName())
                .phoneNumber(dto.getPhoneNumber())
                .zipcode(dto.getZipcode())
                .address(dto.getAddress())
                .build();
        deliveryRepository.insertDelivery(newDelivery);
    }

    public List<DeliveryResDto> deliveryAllByUserId(Long userIdNo) {
        return deliveryRepository.deliveryAllByUserId(userIdNo);
    }

    public DeliveryResDto selectOneByUserIdAndDeliveryName(Long userIdNo, String deliveryName) {
        return deliveryRepository.selectOneByUserIdAndDeliveryName(userIdNo, deliveryName).get();
    }

    public void deleteDelivery(String deliveryName, Long userIdNo) {
        deliveryRepository.deleteDelivery(deliveryName, userIdNo);
    }

    public void updateDelivery(Long userId, DeliveryUpdateDto dto) {
        Optional<DeliveryResDto> findDelivery = deliveryRepository.selectOneByUserIdAndDeliveryName(userId, dto.getUpdateDeliveryName());

        if (findDelivery.isPresent() && !dto.getUpdateDeliveryName().equals(dto.getCurDeliveryName())) {
            String findName = findDelivery.get().getDeliveryName();
            if (findName.equals(dto.getUpdateDeliveryName())) {
                throw new IllegalArgumentException("변경하려는 배송지 이름이 이미 존재합니다.");
            }
        }

        Long deliveryId = deliveryRepository.selectDeliveryId(userId, dto.getCurDeliveryName());
        Delivery newDelivery = Delivery.builder()
                .deliveryId(deliveryId)
                .userIdNo(userId)
                .deliveryName(dto.getUpdateDeliveryName())
                .userName(dto.getUserName())
                .phoneNumber(dto.getPhoneNumber())
                .zipcode(dto.getZipcode())
                .address(dto.getAddress())
                .build();
        deliveryRepository.updateDelivery(newDelivery);
    }
}
