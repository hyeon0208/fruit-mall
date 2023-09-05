package com.fruit.mall.mypage;

import com.fruit.mall.mypage.dto.OrderDetail;
import com.fruit.mall.review.dto.ReviewResDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageRepository myPageRepository;

    public PageInfo<OrderDetail> getOrderDetailsByUserID(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderDetail> orderDetails = myPageRepository.selectOrderDetailsByUserId(userId);
        return new PageInfo<>(orderDetails);
    }
}
