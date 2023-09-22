package com.fruit.mall.config.login.handler;

import com.fruit.mall.cart.CartRepository;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.user.User;
import com.fruit.mall.user.UserRepository;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static com.fruit.mall.user.LoginApiController.LOGIN_USER;

@RequiredArgsConstructor
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        User user = userRepository.selectUserByUserEmail(username, "일반");
        HttpSession session = request.getSession();
        Long cartId = cartRepository.selectUserCartId(user.getUser_id_no());
        session.setAttribute(LOGIN_USER, new SessionUser(user, cartId));
        log.info("로그인에 성공하였습니다. 이메일 : {}", username);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        JsonObject json = new JsonObject();
        json.addProperty("redirectUrl", "/");

        PrintWriter out = response.getWriter();
        out.print(json);
    }
}