package com.fruit.mall.orders;

import com.fruit.mall.orders.dto.OrderReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository implements OrderMapper {
    private final OrderMapper orderMapper;

    @Override
    public OrderReqDto selectOneOrderInfoByProductId(Long productId, int productCount) {
        return orderMapper.selectOneOrderInfoByProductId(productId, productCount);
    }

    @Override
    public OrderReqDto selectOrderInfosByProductId(Long productId) {
        return orderMapper.selectOrderInfosByProductId(productId);
    }
}
