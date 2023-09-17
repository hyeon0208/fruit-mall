package com.fruit.mall.orders;

import com.fruit.mall.orders.dto.OrderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepository implements OrderMapper {
    private final OrderMapper orderMapper;

    @Override
    public void insertOrder(Orders orders) {
        orderMapper.insertOrder(orders);
    }

    @Override
    public OrderInfo selectOneOrderInfoByProductId(Long productId, int productCount, Long userIdNo) {
        return orderMapper.selectOneOrderInfoByProductId(productId, productCount, userIdNo);
    }

    @Override
    public OrderInfo selectOrderInfosByProductId(Long productId, Long userIdNo) {
        return orderMapper.selectOrderInfosByProductId(productId, userIdNo);
    }
}
