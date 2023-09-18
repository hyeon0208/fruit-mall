package com.fruit.mall.mypage;

import com.fruit.mall.cart.CartService;
import com.fruit.mall.cart.dto.CartAddReqDto;
import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.mypage.dto.MyPageSearchCond;
import com.fruit.mall.mypage.dto.OrderDetail;
import com.fruit.mall.mypage.dto.RepurchaseReqDto;
import com.fruit.mall.product.Product;
import com.fruit.mall.product.ProductService;
import com.fruit.mall.product.dto.ProductPriceInfo;
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
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/user/mypage/searchfilter")
    public PageInfo<OrderDetail> myPageSearchFilter(
            @Login SessionUser sessionUser,
            @ModelAttribute MyPageSearchCond cond) {
        PageInfo<OrderDetail> pageInfo = myPageService.getOrderDetailsBySearchFilter(cond, sessionUser.getUserIdNo());
        return pageInfo;
    }

    @PostMapping("/user/mypage/repurchase")
    public void repurchase(@Login SessionUser sessionUser, @RequestBody RepurchaseReqDto repurchaseReqDto) {
        ProductPriceInfo priceInfo = productService.selectPriceAndDiscountById(repurchaseReqDto.getProductId());
        CartAddReqDto dto = CartAddReqDto.builder()
                .productId(repurchaseReqDto.getProductId())
                .productPrice(priceInfo.getProductPrice())
                .productDiscount(priceInfo.getProductDiscount())
                .productCount(repurchaseReqDto.getProductCount()).build();
        cartService.repurchase(sessionUser.getUserIdNo(), sessionUser.getCartId(), dto);
    }

    @PostMapping("/user/mypage/password/confirm")
    public String checkPwd(@Login SessionUser sessionUser, @RequestBody Map<String, String> param) {
        String inputPwd = param.get("inputPwd");
        if (!userService.myPageLoginCheck(sessionUser.getUserIdNo(), inputPwd)) {
            return "비밀번호가 일치하지 않습니다.";
        }
        return "success";
    }

    @PostMapping("/user/mypage/name-check")
    public String checkName(@RequestBody Map<String, String> param) {
        String findUsername = userService.selectUserNameByUserName(param.get("userName"));
        return findUsername != null ? "fail" : "success";
    }

    @PostMapping("/user/mypage/userinfo-update")
    public String updateUserInfo(@Login SessionUser sessionUser, @RequestBody UserInfoUpdateDto dto) {
        userService.updateUserInfo(sessionUser.getUserIdNo(), dto);
        return "success";
    }
}
