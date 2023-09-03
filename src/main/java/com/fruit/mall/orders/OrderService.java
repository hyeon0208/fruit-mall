package com.fruit.mall.orders;

import com.fruit.mall.orderProduct.OrderProduct;
import com.fruit.mall.orderProduct.OrderProductRepository;
import com.fruit.mall.orders.dto.OrderReqDto;
import com.fruit.mall.orders.dto.OrderSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public void insertOrder(Long userId, List<OrderSaveDto> orderSaveDtos) {
        for (OrderSaveDto orderSaveDto : orderSaveDtos) {
            Orders orders = Orders.builder()
                    .userIdNo(userId)
                    .orderPrice(orderSaveDto.getOrderPrice())
                    .orderCount(orderSaveDto.getOrderCount())
                    .receiverName(orderSaveDto.getReceiverName())
                    .phoneNumber(orderSaveDto.getPhoneNumber())
                    .zipcode(orderSaveDto.getZipcode())
                    .address(orderSaveDto.getAddress())
                    .orderRequired(orderSaveDto.getOrderRequired())
                    .orderStatus(orderSaveDto.getOrderStatus())
                    .paymentMethod(orderSaveDto.getPaymentMethod())
                    .build();
            orderRepository.insertOrder(orders);

            OrderProduct orderProduct = OrderProduct.builder()
                    .orderId(orders.getOrderId())
                    .productId(orderSaveDto.getProductId())
                    .orderNumber(orderSaveDto.getOrderNumber())
                    .orderCount(orderSaveDto.getOrderCount())
                    .orderPrice(orderSaveDto.getOrderPrice())
                    .build();

            orderProductRepository.insertOrderProduct(orderProduct);
        }
    }

    public OrderReqDto selectOneOrderInfoByProductId(Long productId, int productCount) {
        return orderRepository.selectOneOrderInfoByProductId(productId, productCount);
    }

    public OrderReqDto selectOrderInfosByProductId(Long productId) {
        return orderRepository.selectOrderInfosByProductId(productId);
    }
}
