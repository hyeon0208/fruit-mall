package com.fruit.mall.mypage;

import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.mypage.dto.MyPageSearchCond;
import com.fruit.mall.mypage.dto.OrderDetail;
import com.fruit.mall.user.dto.UserInfoUpdateDto;
import com.fruit.mall.user.UserService;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MyPageApiController {
    private final MyPageService myPageService;
    private final UserService userService;

    @GetMapping("/user/mypage/searchfilter")
    @ResponseBody
    public PageInfo<OrderDetail> myPageSearchFilter(
            @Login SessionUser sessionUser,
            @ModelAttribute MyPageSearchCond cond) {
        PageInfo<OrderDetail> pageInfo = myPageService.getOrderDetailsBySearchFilter(cond, sessionUser.getUserIdNo());
        return pageInfo;
    }

    @PostMapping("/user/mypage/password/confirm")
    @ResponseBody
    public String checkPwd(@Login SessionUser sessionUser, @RequestBody Map<String, String> param) {
        String inputPwd = param.get("inputPwd");
        if (!userService.myPageLoginCheck(sessionUser.getUserIdNo(), inputPwd)) {
            return "비밀번호가 일치하지 않습니다.";
        }
        return "success";
    }

    @PostMapping("/user/mypage/name-check")
    @ResponseBody
    public String checkName(@RequestBody Map<String, String> param) {
        String findUsername = userService.selectUserNameByUserName(param.get("userName"));
        return findUsername != null ? "fail" : "success";
    }

    @PostMapping("/user/mypage/userinfo-update")
    @ResponseBody
    public String updateUserInfo(@Login SessionUser sessionUser, @RequestBody UserInfoUpdateDto dto) {
        userService.updateUserInfo(sessionUser.getUserIdNo(), dto);
        return "success";
    }
}
