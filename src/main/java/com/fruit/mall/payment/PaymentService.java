package com.fruit.mall.payment;

import com.fruit.mall.orderProduct.OrderProduct;
import com.fruit.mall.orderProduct.OrderProductRepository;
import com.fruit.mall.orders.OrderRepository;
import com.fruit.mall.orders.Orders;
import com.fruit.mall.orders.dto.OrderSaveDto;
import com.fruit.mall.payment.enums.PayStatus;
import com.fruit.mall.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class PaymentService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    public void saveOrder(Long userId, List<OrderSaveDto> orderSaveDtos) {
        int curStock = productRepository.selectProductStock(orderSaveDtos.get(0).getProductId());
        int orderStock = orderSaveDtos.get(0).getOrderCount();

        if (curStock - orderStock < 0) {
            throw new IllegalArgumentException("상품 재고가 부족합니다.");
        }

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
                    .orderStatus(PayStatus.SUCCESS.getStatus())
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
            productRepository.updateProductStock(orderSaveDto.getProductId(), orderSaveDto.getOrderCount());
        }
    }
}
