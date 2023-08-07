package com.fruit.mall.user;

import com.fruit.mall.user.dto.Term;
import com.fruit.mall.user.dto.User;
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
    public User selectUserByUserEmail(String loginUserEmail) {

        return userMapper.selectUserByUserEmail(loginUserEmail);
    }

    @Override
    public Long selectOnlyUserIdNo(String email) {
        return userMapper.selectOnlyUserIdNo(email);
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
}
