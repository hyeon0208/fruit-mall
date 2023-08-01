package com.fruit.mall.admin;

import com.fruit.mall.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService implements AdminMapper {

    private final AdminMapper adminMapper;

    @Override
    public Admin selectAdminById(String id) {
        return adminMapper.selectAdminById(id);
    }

    public boolean loginCheck(String inputId, String inputPwd, Admin findAdmin) {
        if (findAdmin == null || !findAdmin.getId().equals(inputId) || !findAdmin.getPassword().equals(inputPwd)) {
            return false;
        }
        return true;
    }
}
