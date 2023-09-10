package com.fruit.mall.mypage;

import com.fruit.mall.mypage.dto.MyPageSearchCond;
import com.fruit.mall.mypage.dto.OrderDetail;
import com.fruit.mall.user.dto.UserInfoUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyPageMapper {
    List<OrderDetail> selectOrderDetailsByUserId(@Param("userIdNo") Long userIdNo);

    List<OrderDetail> selectOrderDetailsBySearchFilter(@Param("cond") MyPageSearchCond cond, @Param("userIdNo") Long userIdNo);

    UserInfoUpdateDto selectUserByUserId(@Param("userIdNo") Long userIdNo);
}
