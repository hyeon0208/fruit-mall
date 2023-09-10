package com.fruit.mall.mypage;

import com.fruit.mall.mypage.dto.MyPageSearchCond;
import com.fruit.mall.mypage.dto.OrderDetail;
import com.fruit.mall.user.dto.UserInfoUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyPageRepository implements MyPageMapper {
    private final MyPageMapper myPageMapper;

    @Override
    public List<OrderDetail> selectOrderDetailsByUserId(Long userIdNo) {
        return myPageMapper.selectOrderDetailsByUserId(userIdNo);
    }

    @Override
    public List<OrderDetail> selectOrderDetailsBySearchFilter(MyPageSearchCond cond, Long id) {
        return myPageMapper.selectOrderDetailsBySearchFilter(cond, id);
    }

    @Override
    public UserInfoUpdateDto selectUserByUserId(Long userIdNo) {
        return myPageMapper.selectUserByUserId(userIdNo);
    }
}
