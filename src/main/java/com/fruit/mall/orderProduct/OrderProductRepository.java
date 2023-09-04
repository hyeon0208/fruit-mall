package com.fruit.mall.orderProduct;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderProductRepository implements OrderProductMapper {
    private final OrderProductMapper orderProductMapper;

    @Override
    public void insertOrderProduct(OrderProduct orderProduct) {
        orderProductMapper.insertOrderProduct(orderProduct);
    }

    @Override
    public List<Long> selectOPIdByOrderIdAndProductId(Long userIdNo, Long productId) {
        return orderProductMapper.selectOPIdByOrderIdAndProductId(userIdNo, productId);
    }
}
