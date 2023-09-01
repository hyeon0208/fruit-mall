package com.fruit.mall.orderProduct;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderProductRepository implements OrderProductMapper {
    private final OrderProductMapper orderProductMapper;

    @Override
    public void insertOrderProduct(OrderProduct orderProduct) {
        orderProductMapper.insertOrderProduct(orderProduct);
    }
}
