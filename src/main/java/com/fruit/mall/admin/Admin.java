package com.fruit.mall.admin;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String password;

    @Builder
    public Admin(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
