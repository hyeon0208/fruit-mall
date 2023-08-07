package com.fruit.mall.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminRepository implements AdminMapper {
    private final AdminMapper adminMapper;

    @Override
    public Admin selectAdminById(String id) {
        return adminMapper.selectAdminById(id);
    }
}
