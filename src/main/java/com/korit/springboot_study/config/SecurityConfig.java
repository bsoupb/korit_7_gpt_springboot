package com.korit.springboot_study.config;

import com.korit.springboot_study.security.exception.CustomAuthenticationEntryPoint;
import com.korit.springboot_study.security.filter.CustomAuthenticationFilter;
import com.korit.springboot_study.security.filter.JwtAuthenticationFilter;
import com.korit.springboot_study.security.oauth2.OAuth2Service;
import com.korit.springboot_study.security.oauth2.OAuth2SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private OAuth2Service oAuth2Service;

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();      // 서버사이드랜더링 때 사용(RestAPI 때 사용 하지 않음 - 데이터만 응답 / 서버에 인증 번호 저장 X) 정품 인증 역할
        http.httpBasic().disable();
        http.formLogin().disable();
        // 세션을 사용하지 않는 상태로 설정
        // JWT 기반의 인증 방식 -> 서버가 클라이언트의 인증 정보를 세션에 저장하지 않고 매 요청마다 인증처리
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                        .authenticationEntryPoint(customAuthenticationEntryPoint);

        // OAuth2 로그인 설정
        http.oauth2Login()
                .successHandler(oAuth2SuccessHandler)   // 로그인 성공 후 실행할 커스텀 핸들러 지정
                .userInfoEndpoint() // 로그인한 사용자의 정보를 가져오는 엔드포인트를 설정
                .userService(oAuth2Service);    // 사용자 정보를 처리할 커스텀 서비스 지정

        http.authorizeRequests()
                .antMatchers(           // 로그인 없이 접근 가능해야 하는 URI 예외 설정
                        "/swagger-ui/**",
                        "/v2/api-docs/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/server/hc"
                )
                .permitAll()
                .antMatchers("/api/auth/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/post/**")
                .permitAll()
                .anyRequest()       // 어떠한 URI로 접근하던지 인증이 필요함을 설정
                .authenticated();


    }
}
