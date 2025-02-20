package com.korit.springboot_study.security.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

// 로그인 성공 후 실행되는 핸들러
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Value("${oauth2.client.redirect_uri}")
    private String client_redirect_uri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 로그인한 OAuth2 사용자 정보 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        // OAuth2 제공자로부터 받은 고유 사용자 ID
        String oauth2Id = oAuth2User.getAttribute("oauth2Id");
        // OAuth2 제공자(Google 등) 정보
        String provider = oAuth2User.getAttribute("provider");
        // 클라이언트(프론트엔드)로 쿼리 파라미터를 포함하여 리다이렉트
        response.sendRedirect(client_redirect_uri + "?oauth2Id=" + oauth2Id + "&provider=" + provider);
    }
}
