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

    public Boolean existsOrderProductByUser(Long userIdNo, Long productId) {
        List<Long> orderProductId = orderProductRepository.selectOPIdByOrderIdAndProductId(userIdNo, productId);
        if (orderProductId.isEmpty()) {
            return false;
        }
        return true;
    }
}
