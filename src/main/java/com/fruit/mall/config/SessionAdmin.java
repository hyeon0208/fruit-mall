package com.fruit.mall.config;

import com.fruit.mall.admin.Admin;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionAdmin implements Serializable {
    private String adminId;

    public SessionAdmin(Admin admin) {
        this.adminId = admin.getId();
    }
}
