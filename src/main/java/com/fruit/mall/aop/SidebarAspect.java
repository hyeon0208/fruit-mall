package com.fruit.mall.aop;

import com.fruit.mall.cart.CartService;
import com.fruit.mall.config.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.like.LikeService;
import com.fruit.mall.product.RecentProductService;
import com.fruit.mall.product.dto.RecentProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.fruit.mall.product.RecentProductService.RECENT_PRODUCTS;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SidebarAspect {
    private final LikeService likeService;
    private final CartService cartService;
    private final RecentProductService recentProductService;

    @Around("execution(* com.fruit.mall..*Controller.*(..)) && args(sessionUser,model,..) && !execution(* com.fruit.mall..*Controller.goMyPageUserInfoEdit(..))")
    public Object addSidebar(ProceedingJoinPoint joinPoint, @Login SessionUser sessionUser, Model model) throws Throwable {
        if (sessionUser != null) {
            Long userId = sessionUser.getUserIdNo();
            int likesCount = likeService.countLikesByUserId(userId);
            int userCartsCount = cartService.countCartByUserId(userId);

            model.addAttribute("userId", userId);
            model.addAttribute("likesCount", likesCount);
            model.addAttribute("userCartsCount", userCartsCount);
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        List<RecentProduct> recentProducts = recentProductService.getRecentProductsFromCookie(request);
        if (recentProducts != null) {
            model.addAttribute(RECENT_PRODUCTS, recentProducts);
        }

        log.info("Sidebar Aspect 적용 메서드 정보 : {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
