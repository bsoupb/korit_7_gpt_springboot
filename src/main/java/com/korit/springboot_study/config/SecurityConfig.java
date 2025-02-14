package com.korit.springboot_study.config;

import com.korit.springboot_study.security.exception.CustomAuthenticationEntryPoint;
import com.korit.springboot_study.security.filter.CustomAuthenticationFilter;
import com.korit.springboot_study.security.filter.JwtAuthenticationFilter;
import com.korit.springboot_study.security.oauth2.OAuth2Service;
import com.korit.springboot_study.security.oauth2.OAuth2SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        http.csrf().disable();      // 서버사이드랜더링 때 사용(RestAPI 때 사용 하지 않음 - 데이터만 응답 / 서버에 인증 번호 저장 X) 정품 인증 역할
        http.httpBasic().disable();
        http.formLogin().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                        .authenticationEntryPoint(customAuthenticationEntryPoint);

        http.oauth2Login()
                .successHandler(oAuth2SuccessHandler)
                .userInfoEndpoint()
                .userService(oAuth2Service);

        http.authorizeRequests()
                .antMatchers(           // 로그인 없이 접근 가능해야 하는 URI 예외 설정
                        "/swagger-ui/**",
                        "/v2/api-docs/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**"
                )
                .permitAll()
                .antMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()       // 어떠한 URI로 접근하던지 인증이 필요함을 설정
                .authenticated();


    }
}
