package com.fruit.mall.user;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleTypeHandler extends BaseTypeHandler<Role> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Role parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public Role getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value != null ? Role.valueOf(value) : null;
    }

    @Override
    public Role getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value != null ? Role.valueOf(value) : null;
    }

    @Override
    public Role getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value != null ? Role.valueOf(value) : null;
    }
}