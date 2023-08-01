package com.fruit.mall.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    Admin selectAdminById(@Param("id") String id);
}
