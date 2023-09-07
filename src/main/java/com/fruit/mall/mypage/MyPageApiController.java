package com.fruit.mall.mypage;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.mypage.dto.MyPageSearchCond;
import com.fruit.mall.mypage.dto.OrderDetail;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MyPageApiController {
    private final MyPageService myPageService;

    @GetMapping("/user/mypage/searchfilter")
    @ResponseBody
    public PageInfo<OrderDetail> myPageSearchFilter(
            @Login SessionUser sessionUser,
            @ModelAttribute MyPageSearchCond cond) {
        PageInfo<OrderDetail> pageInfo = myPageService.getOrderDetailsBySearchFilter(cond, sessionUser.getUserIdNo());
        return pageInfo;
    }
}
