package com.fruit.mall.aop;

import com.fruit.mall.cart.CartService;
import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.like.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.fruit.mall.product.RecentProductService.RECENT_PRODUCTS;
import static com.fruit.mall.user.MainController.recentProducts;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SidebarAspect {
    private final LikeService likeService;
    private final CartService cartService;

    @Around("execution(* com.fruit.mall..*Controller.*(..)) && args(sessionUser,model,..)")
    public Object addSidebar(ProceedingJoinPoint joinPoint, @Login SessionUser sessionUser, Model model) throws Throwable {
        if (sessionUser != null) {
            int likesCount = likeService.countLikesByUserId(sessionUser.getUserIdNo());
            model.addAttribute("likesCount", likesCount);

            int userCartsCount = cartService.countCartByUserId(sessionUser.getUserIdNo());
            model.addAttribute("userCartsCount", userCartsCount);
        }
        if (recentProducts != null) {
            model.addAttribute(RECENT_PRODUCTS, recentProducts);
        }

        log.info("Sidebar Aspect 적용 메서드 정보 : {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
