package com.korit.springboot_study.security.jwt;

import com.korit.springboot_study.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private Key key;

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));   // 생성이 될 때 key 값이 완성됨
    }

    private Date getExpireDate() {
        return new Date(new Date().getTime() + (1000l * 60 * 60 * 24 * 365));
    }

    public String createAccessToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getUserId())  // 사용자 ID 저장
                .claim("roles", user.getUserRoles().stream().map(userRole -> userRole.getRole().getRoleName()).collect(Collectors.toList()))
                .setExpiration(getExpireDate())     // 토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)    // 토큰 서명
                .compact();
    }
    // payload -> claim
    // security

    public boolean validateToken(String token) {
        return token != null && token.startsWith("Bearer ");
    }

    public Claims parseToken(String token) {
        Claims claims = null;
        try {
            JwtParser parser = Jwts.parserBuilder()     // JWT를 해석할 수 있는 Parser 객체 생성
                    .setSigningKey(key)     // 토큰이 변조되지 않았는지 서명을 검증하기 위해 비밀 키 사용
                    .build();
            claims = parser.parseClaimsJws(token).getBody();  // JWT를 파싱하고 안에 있는 Claims(사용자 정보) 추출
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    // Authorization -> AccessToken(Bearer ?????.?????.?????)
    public String removeBearer(String bearerToken) {
        String token = null;
        final String BEARER_KEYWORD = "Bearer ";
        if(bearerToken.startsWith(BEARER_KEYWORD)) {
            token = bearerToken.substring(BEARER_KEYWORD.length());
        }
        return token;
    }
}
