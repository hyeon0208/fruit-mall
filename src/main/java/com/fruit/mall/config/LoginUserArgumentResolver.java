package com.fruit.mall.config;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.fruit.mall.user.UserLoginController.LOGIN_USER;

/**
 * 사용자의 세션 정보를 컨트롤러 메서드의 파라미터로 전달하기 위해 사용되는 클래스
 */
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
     * 파라미터에 @Login 어노테이션이 붙어 있고, 파라미터 클래스 타입이 SessionUser.class인 경우 true를 반환.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(Login.class) != null;
        boolean isSessionClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isSessionClass;
    }

    /**
     * 파라미터에 전달할 객체를 생성한다.
     * 여기선 세션에서 객체를 가져온다.
     */
    @Override
    public SessionUser resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        HttpSession session = request.getSession(false);

        if (session == null) {
            return new SessionUser();
        }

        return (SessionUser) session.getAttribute(LOGIN_USER);
    }
}
