package com.korit.springboot_study.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
// OAuth2 로그인 시 사용자 정보 처리
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    // OAuth2 로그인 시 실행
    // userRequest: OAuth2 로그인 요청 정보
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println(userRequest.getAccessToken().getTokenValue());

        // OAuth2 제공자 이름 가져옴 ex) google, github ... -> 로그인 방식 차이 처리
        String provider = userRequest.getClientRegistration().getRegistrationId();    // *getRegistrationId()

        // OAuth2 사용자 정보 가져옴
        DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        // OAuth2 제공자로부터 받은 원본 사용자 정보
        Map<String, Object> attributes = oAuth2User.getAttributes();
        // 가공 후 반환할 사용자 정보
        Map<String, Object> newAttributes = new HashMap<>();
        // 사용자의 권한 정보
        Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();

        if(provider.equals("google")) {
            newAttributes.put("oauth2Id", attributes.get("sub"));
        }
        newAttributes.put("provider", provider);

        return new DefaultOAuth2User(authorities, newAttributes, "oauth2Id");
    }
}
