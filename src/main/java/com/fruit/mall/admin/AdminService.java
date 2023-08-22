package com.fruit.mall.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Admin selectAdminById(String id) {
        return adminRepository.selectAdminById(id);
    }

    public boolean loginCheck(String inputId, String inputPwd, Admin findAdmin) {
        if (findAdmin == null || !findAdmin.getId().equals(inputId) || !findAdmin.getPassword().equals(inputPwd)) {
            return false;
        }
        return true;
    }
}
