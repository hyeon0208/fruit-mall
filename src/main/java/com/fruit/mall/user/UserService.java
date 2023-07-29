package com.fruit.mall.user;

import com.fruit.mall.user.dto.Term;
import com.fruit.mall.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserMapper {

    private final UserMapper userMapper;

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User selectByUserEmail(String loginUserEmail) {
        User user = userMapper.selectByUserEmail(loginUserEmail);
        if (user.getUser_email() != loginUserEmail) {
        }
        return userMapper.selectByUserEmail(loginUserEmail);
    }

    @Override
    public Long selectOnlyUserIdNo(String email) {
        return userMapper.selectOnlyUserIdNo(email);
    }

    @Override
    public void insertFirstTerm(Term fristTerm) {
        userMapper.insertFirstTerm(fristTerm);
    }

    @Override
    public void insertSecondTerm(Term secondTerm) {
        userMapper.insertFirstTerm(secondTerm);
    }
}
