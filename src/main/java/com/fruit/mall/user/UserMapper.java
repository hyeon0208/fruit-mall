package com.fruit.mall.user;

import com.fruit.mall.user.dto.Term;
import com.fruit.mall.user.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    void insertUser(User user);

    User selectUserByUserEmail(String userEmail);

    Long selectOnlyUserIdNo(String email);

    void insertTerm(Term term);

    String selectEmailByUserEmail(String user_email);

    String selectUserNameByUserName(String user_name);

    void updateNewPassword(@Param("user_email")String user_email, @Param("user_pwd")String user_pwd);
}
