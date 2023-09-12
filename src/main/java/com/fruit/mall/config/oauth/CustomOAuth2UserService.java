package com.fruit.mall.config.oauth;

import com.fruit.mall.config.SessionUser;
import com.fruit.mall.user.Role;
import com.fruit.mall.user.User;
import com.fruit.mall.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

import static com.fruit.mall.user.UserLoginController.LOGIN_USER;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;


    /**
     * OAuth 2 프로바이더로 부터 사용자 정보를 가져오는 메서드.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 현재 로그인 진행 중인 서비스를 구분하는 코드 (구글, 네이버 등)

        // 소셜 로그인 사용자를 구분하는 key값을 가져옴 (구글은 sub, naver kakao는 id)
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes, registrationId);
        httpSession.setAttribute(LOGIN_USER, new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.name())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    /**
     *  이미 가입된 유저일 경우와 새로운 유저일 때 분기처리
     */
    private User saveOrUpdate(OAuthAttributes attributes, String registrationId) {
        User user = userRepository.selectUserByUserEmail(attributes.getEmail());
        if (user == null) {
            user = attributes.toEntity(registrationId);
            userRepository.insertOAuthUser(user);
        }
        return user;
    }
}
