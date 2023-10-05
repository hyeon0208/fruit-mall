package com.fruit.mall.payment;

import com.fruit.mall.annotaion.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.orders.dto.OrderSaveDto;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

@RestController
@Slf4j(topic = "elk")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PaymentApiController {
    private final PaymentService paymentService;
    private final RefundService refundService;

    private IamportClient iamportClient;

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secretkey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }

    @PostMapping("/payment")
    public ResponseEntity<String> paymentComplete(@Login SessionUser sessionUser, @RequestBody List<OrderSaveDto> orderSaveDtos) throws IOException {
        String orderNumber =orderSaveDtos.get(0).getOrderNumber();
        try {
            Long userId = sessionUser.getUserIdNo();
            paymentService.saveOrder(userId, orderSaveDtos);
            log.info("결제 성공 : 주문 번호 {}", orderNumber);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.info("주문 상품 환불 진행 : 주문 번호 {}", orderNumber);
            String token = refundService.getToken(apiKey, secretKey);
            refundService.refundRequest(token, orderNumber, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/payment/{imp_uid}")
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) {
        IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
        log.info("결제 요청 응답. 결제 내역 - 주문 번호: {}", payment.getResponse().getMerchantUid());
        return payment;
    }
}
