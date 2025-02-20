package com.korit.springboot_study.security.filter;

import com.korit.springboot_study.repository.UserRepository;
import com.korit.springboot_study.security.jwt.JwtProvider;
import com.korit.springboot_study.security.principal.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements Filter {
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // Bearer Token(JWT)
        String authorization = request.getHeader("Authorization");

        if(jwtProvider.validateToken(authorization)) {
            setJwtAuthentication(authorization);
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    private void setJwtAuthentication(String bearerToken) {
        String accessToken = jwtProvider.removeBearer(bearerToken);
        Claims claims = jwtProvider.parseToken(accessToken);
        if(claims == null) {
            return;
        }
        int userId = Integer.parseInt(claims.get("userId").toString());
        userRepository.findById(userId).ifPresent(user -> {     //user 객체 찾기
            SecurityContext securityContext = SecurityContextHolder.getContext();
            PrincipalUser principalUser = new PrincipalUser(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser, principalUser.getPassword(), principalUser.getAuthorities());
            securityContext.setAuthentication(authentication);
        });

    }
}
