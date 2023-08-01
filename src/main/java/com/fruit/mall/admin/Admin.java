package com.fruit.mall.admin;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Admin {

    private String id;
    private String password;

    @Builder
    public Admin(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
