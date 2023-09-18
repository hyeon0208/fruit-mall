package com.fruit.mall.user.dto;

import com.fruit.mall.user.Role;
import com.fruit.mall.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UsernameUpdateDto {
    private String newName;
    private String curName;
    private String userEmail;
    private String loginMethod;
    private Role role;

    @Builder
    public UsernameUpdateDto(String newName, String curName, String userEmail, String loginMethod, Role role) {
        this.newName = newName;
        this.curName = curName;
        this.userEmail = userEmail;
        this.loginMethod = loginMethod;
        this.role = role;
    }

    public User toEntity(UsernameUpdateDto dto) {
        return User.builder()
                .user_name(dto.getNewName())
                .user_email(dto.getUserEmail())
                .loginMethod(dto.loginMethod)
                .role(dto.getRole())
                .build();
    }
}
