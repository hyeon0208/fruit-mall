package com.fruit.mall.user.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String user_email;
    private String user_name;
    private String user_pwd;
    private String user_state;

    @Builder
    public User(String user_email, String user_name, String user_pwd) {
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_pwd = user_pwd;
    }
}
