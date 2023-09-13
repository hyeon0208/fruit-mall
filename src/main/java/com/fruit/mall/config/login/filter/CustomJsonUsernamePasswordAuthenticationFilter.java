package com.fruit.mall.config.login.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class CustomJsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private ObjectMapper objectMapper;

    public CustomJsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher("/user/login", "POST")); // 해당 경로 요청을 처리하기 위한 설정
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getContentType() == null || !request.getContentType().equals("application/json")  ) {
            throw new AuthenticationServiceException("잘못된 요청 형식입니다. = " + request.getContentType());
        }
        Map<String, String> loginForm = objectMapper.readValue(request.getInputStream(), Map.class);

        String userEmail = loginForm.get("user_email");
        String pwd = loginForm.get("user_pwd");

        // 요청 받은 값으로 객체를 생성해 실제 인증 절차를 진행.
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userEmail, pwd);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
