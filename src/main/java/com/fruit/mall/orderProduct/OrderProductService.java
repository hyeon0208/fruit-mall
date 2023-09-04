package com.fruit.mall.orderProduct;

import com.fruit.mall.orders.dto.OrderSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public void insertOrderProduct(List<Long> orderIds,  List<OrderSaveDto> orderSaveDtos) {
        for (int i = 0; i < orderIds.size(); i++) {
            OrderProduct orderProduct = OrderProduct.builder()
                    .orderId(orderIds.get(i))
                    .productId(orderSaveDtos.get(i).getProductId())
                    .orderNumber(orderSaveDtos.get(i).getOrderNumber())
                    .orderCount(orderSaveDtos.get(i).getOrderCount())
                    .orderPrice(orderSaveDtos.get(i).getOrderPrice())
                    .build();
            orderProductRepository.insertOrderProduct(orderProduct);
        }
    }

    public Boolean existsOrderProductByUser(Long userIdNo, Long productId) {
        List<Long> orderProductId = orderProductRepository.selectOPIdByOrderIdAndProductId(userIdNo, productId);
        if (orderProductId.isEmpty()) {
            return false;
        }
        return true;
    }
}
