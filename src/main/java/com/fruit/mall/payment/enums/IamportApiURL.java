package com.fruit.mall.payment.enums;

public enum IamportApiURL {
    GET_TOKEN_URL("https://api.iamport.kr/users/getToken"),
    CANCEL_URL("https://api.iamport.kr/payments/cancel");
    private String url;

    public String getURL() {
        return url;
    }

    IamportApiURL(String url) {
        this.url = url;
    }
}
