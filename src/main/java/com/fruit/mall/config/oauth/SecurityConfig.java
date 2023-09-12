package com.fruit.mall.config.oauth;

import com.fruit.mall.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/user/searchfilter", "/user/css/**", "/user/img/**", "/user/js/**", "/login", "/user/cart", "/user/detail/**", "/user/review/**", "/findPw", "/changePw", "/changeNewPw", "/user/like/**", "/join/**", "/user/login", "/user/check-login").permitAll()
                .antMatchers("/user/mypage", "/confirm/payment", "/user/order/**", "/order/**", "/payment/**").hasAuthority(Role.USER.name()) // 해당 주소는 USER 권한을 가진 사람만 가능
                .anyRequest().authenticated()
                .and()
                .logout().invalidateHttpSession(true).logoutSuccessUrl("/")
                .and()
                .oauth2Login() // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                .loginPage("/login")
                .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당
                .userService(customOAuth2UserService) // 소셜 로그인 성공 시 후속 조치를 진행할 OAuth2UserService 인터페이스의 구현체 등록
                .and()
                .defaultSuccessUrl("/", true);
        return http.build();
    }
}
