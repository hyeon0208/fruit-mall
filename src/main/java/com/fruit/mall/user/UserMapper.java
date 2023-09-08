package com.fruit.mall.user;

import com.fruit.mall.term.Term;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    void insertUser(User user);

    User selectUserByUserEmail(@Param("user_email") String user_email);

    String selectPwdById(@Param("userIdNo") Long userIdNo);

    void insertTerm(Term term);

    String selectEmailByUserEmail(String user_email);

    String selectUserNameByUserName(String user_name);

    void updateNewPassword(@Param("user_email")String user_email, @Param("user_pwd")String user_pwd);
}
