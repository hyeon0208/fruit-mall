package com.fruit.mall.aop;

import com.fruit.mall.config.SessionUser;
import com.fruit.mall.notifications.NotificationsService;
import com.fruit.mall.notifications.dto.NotificationsResDto;
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
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.fruit.mall.user.LoginApiController.LOGIN_USER;

@Aspect
@Slf4j(topic = "elk")
@Component
@RequiredArgsConstructor
public class NotificationsAspect {
    private final NotificationsService notificationsService;


    @Around("execution(String com.fruit.mall..*Controller.*(..)) && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object showNotifications(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        SessionUser sessionUser = (SessionUser) session.getAttribute(LOGIN_USER);

        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof Model) {
                Model model = (Model) arg;
                if (sessionUser != null){
                    List<NotificationsResDto> notificationsList = notificationsService.selectMessagesByUserId(sessionUser.getUserIdNo());
                    model.addAttribute("notificationsList", notificationsList);
                    log.info("Notifications Aspect 적용 메서드 정보 : {}", joinPoint.getSignature());
                }
            }
        }

        return joinPoint.proceed();
    }
}
