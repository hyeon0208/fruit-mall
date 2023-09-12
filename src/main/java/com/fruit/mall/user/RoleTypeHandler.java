package com.fruit.mall.user;

import org.apache.ibatis.type.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Role.class)
public class RoleTypeHandler implements TypeHandler<Role> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Role parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getKey());
    }

    @Override
    public Role getResult(ResultSet rs, String columnName) throws SQLException {
        String roleKey = rs.getString(columnName);
        return getRole(roleKey);
    }

    @Override
    public Role getResult(ResultSet rs, int columnIndex) throws SQLException {
        String roleKey = rs.getString(columnIndex);
        return getRole(roleKey);
    }

    @Override
    public Role getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String roleKey = cs.getString(columnIndex);
        return getRole(roleKey);
    }

    // 실제 객체를 구성하는 메서드
    private Role getRole(String key) {
        if (key == null) {
            return Role.GUEST; // 혹은 null을 반환하거나 예외를 던질 수 있습니다.
        }

        switch (key) {
            case "ROLE_USER":
                return Role.USER;

            case "ROLE_ADMIN":
                return Role.ADMIN;

            default:
                return Role.GUEST;
        }
    }
}