package com.fruit.mall.user;

import com.fruit.mall.user.dto.Term;
import com.fruit.mall.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserMapper {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean loginCheck(String pwd, User loginUser) {
        if (loginUser == null || !passwordEncoder.matches(pwd, loginUser.getUser_pwd())) {
            return false;
        }
        return true;
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User selectUserByUserEmail(String loginUserEmail) {

        return userMapper.selectUserByUserEmail(loginUserEmail);
    }

    @Transactional(readOnly = true)
    @Override
    public Long selectOnlyUserIdNo(String email) {
        return userMapper.selectOnlyUserIdNo(email);
    }

    @Override
    public void insertTerm(Term term) {


        userMapper.insertTerm(term);
    }

    @Transactional(readOnly = true)
    @Override
    public String selectEmailByUserEmail(String user_email) {
        return userMapper.selectEmailByUserEmail(user_email);
    }

    @Transactional(readOnly = true)
    @Override
    public String selectUserNameByUserName(String user_name) {
        return userMapper.selectUserNameByUserName(user_name);
    }
}
