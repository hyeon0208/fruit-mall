package com.fruit.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

//@Configuration
//@EnableWebSecurity
//public class LoginCheckConfig {
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/user/images/**", "/user/js/**", "/user/img/**", "/user/favicon.ico");
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/user/login").permitAll()
//                .antMatchers("/user/mypage", "/user/logout", "/user/mypage_delivery").authenticated()
//                .antMatchers("/**").permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/user/login")
//                .defaultSuccessUrl("/", true)
//                .permitAll(); // 해당 url은 USER 권한을 가진 사람만 가능
//
//        return http.build();
//    }
//
//}
