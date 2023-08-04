package com.fruit.mall.config;

import com.fruit.mall.admin.Admin;
import com.fruit.mall.user.dto.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String id;

    public SessionUser(User user) {
        this.name = user.getUser_name();
    }

    public SessionUser(Admin admin) {
        this.id = admin.getId();
    }
}
