package com.fruit.mall.orders;

import com.fruit.mall.orders.dto.OrderReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<OrderReqDto> selectOneOrderInfoByProductId(Long productId, int productCount) {
        return orderRepository.selectOneOrderInfoByProductId(productId, productCount);
    }
}
