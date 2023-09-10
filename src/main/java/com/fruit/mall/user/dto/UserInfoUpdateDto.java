package com.fruit.mall.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserInfoUpdateDto {
    private String userEmail;
    private String userName;
    private String userPwd;

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
