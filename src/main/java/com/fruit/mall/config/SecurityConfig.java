package com.fruit.mall.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fruit.mall.config.login.filter.JsonAuthenticationFilter;
import com.fruit.mall.config.login.handler.CustomAuthenticationFailureHandler;
import com.fruit.mall.config.login.handler.CustomAuthenticationSuccessHandler;
import com.fruit.mall.config.login.service.CustomUserDetailsService;
import com.fruit.mall.config.oauth.service.CustomOAuth2UserService;
import com.fruit.mall.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** 비동기 요청 - 응답시 사용.
     * 입력된 HTTP 요청의 사용자 자격 증명 값을 추출하기 위한 UsernamePasswordAuthenticationFilter의 구현체
     * 추출한 값으로 UsernamePasswordAuthenticationToken 미인증 상태 객체를 생성해 AuthenticationManager에게 전달.
     */
    @Bean
    public JsonAuthenticationFilter jsonAuthenticationFilter() {
        JsonAuthenticationFilter jsonFilter = new JsonAuthenticationFilter(new ObjectMapper());
        jsonFilter.setAuthenticationManager(authenticationManager());
        jsonFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        jsonFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        return jsonFilter;
    }
    
    /**
     * 위임받은 실제 인증 작업 수행.
     * 미인증 UsernamePasswordAuthenticationToken 객체를 받아, 객체에 포함되어 있는 자격 증명이 유효한지 검사.
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable() // 비동기 요청을 받기 위해, 기본 방식인 동기 요청 작업 비활성화
                .httpBasic().disable()
                .csrf().disable().headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/",  "/changePw", "/findPw", "/api/v1/**", "/user/css/**", "/user/img/**", "/user/js/**", "/user/login", "/login",  "/user/cart", "/user/detail/**", "/user/review/**", "/join/**", "/join", "/profile").permitAll()
                    .antMatchers("/user/mypage", "/confirm/payment", "/user/order/**", "/order/**", "/payment/**").hasAuthority(Role.USER.name()) // 해당 주소는 USER 권한을 가진 사람만 가능
                    .anyRequest().authenticated()
                .and()
                    .logout().invalidateHttpSession(true).logoutSuccessUrl("/")
                .and()
                    .oauth2Login() // OAuth2 로그인 설정
                    .defaultSuccessUrl("/", true)
                    .loginPage("/login")
                    .userInfoEndpoint() // 이 체인 다음에 로그인 성공 이후 사용자 정보를 가져오기 위한 설정을 담당하는 객체 등록
                    .userService(customOAuth2UserService); // OAuth2UserService 구현체를 등록

        http.addFilterBefore(jsonAuthenticationFilter(), LogoutFilter.class);
        return http.build();
    }
}
