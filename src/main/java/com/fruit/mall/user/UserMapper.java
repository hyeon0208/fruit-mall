package com.fruit.mall.user;

import com.fruit.mall.term.Term;
import com.fruit.mall.user.dto.UserInfoUpdateDto;
import com.fruit.mall.user.dto.UsernameUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    void insertUser(User user);

    void insertOAuthUser(User user);

    User selectUserByUserEmail(@Param("user_email") String user_email, @Param("loginMethod") String loginMethod);

    String selectPwdById(@Param("userIdNo") Long userIdNo);

    void insertTerm(Term term);

    String selectEmailByUserEmail(String user_email);

    String selectUserNameByUserName(String user_name);

    void updateNewPassword(@Param("user_email")String user_email, @Param("user_pwd")String user_pwd);

    void updateUserInfo(@Param("userIdNo") Long userIdNo, @Param("dto") UserInfoUpdateDto dto);

    void updateUserName(UsernameUpdateDto usernameUpdateDto);
}
