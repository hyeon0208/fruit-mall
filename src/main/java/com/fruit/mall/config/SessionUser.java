package com.fruit.mall.config;

import com.fruit.mall.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private static final long serialVersionUID = -6033498746532854616L;

    private String email;
    private String name;

    public SessionUser(User user) {
        this.email = user.getUser_email();
        this.name = user.getUser_name();
    }
}
