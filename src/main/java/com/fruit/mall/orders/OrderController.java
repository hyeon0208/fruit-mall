package com.fruit.mall.orders;

import com.fruit.mall.cart.CartService;
import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.like.LikeService;
import com.fruit.mall.orders.dto.OrderReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import static com.fruit.mall.user.MainController.recentProducts;
import static com.fruit.mall.product.RecentProductController.RECENT_PRODUCTS;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final LikeService likeService;
    private final CartService cartService;

    @GetMapping("/user/order/one/{productId}/{productCount}")
    public String orderOneProduct(@Login SessionUser sessionUser, @PathVariable Long productId, @PathVariable int productCount, Model model) {

        int likesCount = likeService.countLikesByUserId(sessionUser.getUserIdNo());
        model.addAttribute("likesCount", likesCount);

        int userCartsCount = cartService.countCartByUserId(sessionUser.getUserIdNo());
        model.addAttribute("userCartsCount", userCartsCount);

        model.addAttribute(RECENT_PRODUCTS, recentProducts);

        List<OrderReqDto> orderReqDtos = orderService.selectOneOrderInfoByProductId(productId, productCount);
        model.addAttribute("products", orderReqDtos);
        model.addAttribute("totalSize", orderReqDtos.size());
        return "user/payment";
    }
}
