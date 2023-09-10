package com.fruit.mall.user;

import com.fruit.mall.term.Term;
import com.fruit.mall.user.dto.UserInfoUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserMapper {
    private final UserMapper userMapper;

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public String selectPwdById(Long userIdNo) {
        return userMapper.selectPwdById(userIdNo);
    }

    @Override
    public User selectUserByUserEmail(String user_email) {
        return userMapper.selectUserByUserEmail(user_email);
    }

    @Override
    public void insertTerm(Term term) {
        userMapper.insertTerm(term);
    }

    @Override
    public String selectEmailByUserEmail(String user_email) {
        return userMapper.selectEmailByUserEmail(user_email);
    }

    @Override
    public String selectUserNameByUserName(String user_name) {
        return userMapper.selectUserNameByUserName(user_name);
    }

    @Override
    public void updateNewPassword(String user_email, String user_pwd) {
        userMapper.updateNewPassword(user_email, user_pwd);
    }

    @Override
    public void updateUserInfo(Long userIdNo, UserInfoUpdateDto dto) {
        userMapper.updateUserInfo(userIdNo, dto);
    }
}
