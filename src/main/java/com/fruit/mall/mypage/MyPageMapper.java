package com.fruit.mall.mypage;

import com.fruit.mall.mypage.dto.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyPageMapper {
    List<OrderDetail> selectOrderDetailsByUserId(@Param("userIdNo") Long userIdNo);
}
