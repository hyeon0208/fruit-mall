package com.fruit.mall.config;

import com.fruit.mall.admin.Admin;
import com.fruit.mall.user.User;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class SessionUser implements Serializable {
    private static final long serialVersionUID = -6033498746532854616L;

    private Long userIdNo;
    private String name;
    private String id;
    private String userType;

    public SessionUser(User user) {
        this.name = user.getUser_name();
        this.userIdNo = user.getUser_id_no();
        this.userType = "loginUser";
    }

    public SessionUser(Admin admin) {
        this.id = admin.getId();
        this.userType = "loginAdmin";
    }
}
