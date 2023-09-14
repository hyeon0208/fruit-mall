package com.fruit.mall.orders;

import com.fruit.mall.orders.dto.OrderReqDto;
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
    public OrderReqDto selectOneOrderInfoByProductId(Long productId, int productCount, Long userIdNo) {
        return orderMapper.selectOneOrderInfoByProductId(productId, productCount, userIdNo);
    }

    @Override
    public OrderReqDto selectOrderInfosByProductId(Long productId, Long userIdNo) {
        return orderMapper.selectOrderInfosByProductId(productId, userIdNo);
    }
}
