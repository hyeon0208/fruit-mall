package com.fruit.mall.config;

import com.fruit.mall.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class SessionUser implements Serializable {
    private static final long serialVersionUID = -6033498746532854616L;

    private Long userIdNo;
    private Long cartId;
    private String name;
    private String loginMethod;

    public SessionUser(User user, Long cartId) {
        this.userIdNo = user.getUser_id_no();
        this.cartId = cartId;
        this.name = user.getUser_name();
        this.loginMethod = user.getLoginMethod();
    }
}
