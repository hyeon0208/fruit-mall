package com.fruit.mall.user;

import com.fruit.mall.user.dto.Term;
import com.fruit.mall.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    void insertUser(User user);

    User selectByUserEmail(String userEmail);

    Long selectOnlyUserIdNo(String email);

    void insertFirstTerm(Term term);

    void insertSecondTerm(Term term);
}
