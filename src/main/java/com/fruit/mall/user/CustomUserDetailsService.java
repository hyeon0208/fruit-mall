package com.fruit.mall.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String user_email) throws UsernameNotFoundException {
        User user = userRepository.selectUserByUserEmail(user_email);
        if (user == null) {
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUser_email(),
                user.getUser_pwd(),
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.name()))
        );
    }
}