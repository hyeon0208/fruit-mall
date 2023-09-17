package com.fruit.mall.orders;

import com.fruit.mall.orders.dto.OrderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderInfo selectOneOrderInfoByProductId(Long productId, int productCount, Long userIdNo) {
        return orderRepository.selectOneOrderInfoByProductId(productId, productCount, userIdNo);
    }

    public OrderInfo selectOrderInfosByProductId(Long productId, Long userIdNo) {
        return orderRepository.selectOrderInfosByProductId(productId, userIdNo);
    }
}
