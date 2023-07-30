package com.fruit.mall.user;

import com.fruit.mall.user.dto.Term;
import com.fruit.mall.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void insertUser(User user);

    User selectByUserEmail(String userEmail);

    Long selectOnlyUserIdNo(String email);

    void insertTerm(Term term);

}
