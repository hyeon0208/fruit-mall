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
    private String name;

    public SessionUser(User user) {
        this.userIdNo = user.getUser_id_no();
        this.name = user.getUser_name();
    }
}
