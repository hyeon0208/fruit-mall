package com.fruit.mall.user;

import com.fruit.mall.user.Role;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long user_id_no;
    private String user_email;
    private String user_name;
    private String user_pwd;
    private Role user_status;
    private String loginMethod;

    @Builder
    public User(String user_email, String user_name, String user_pwd, Role user_status, String loginMethod) {
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_pwd = user_pwd;
        this.user_status = user_status;
        this.loginMethod = loginMethod;
    }
}
