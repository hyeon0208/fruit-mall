package com.fruit.mall.mypage;

import com.fruit.mall.mypage.dto.OrderDetail;
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
}
