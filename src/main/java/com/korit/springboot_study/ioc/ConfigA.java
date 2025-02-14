package com.korit.springboot_study.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 서버를 실행했을 때 호출을 하지 않아도 실행됨
@Configuration
public class ConfigA {
    @Bean
    // 1. 어떤 클래스를 만들어서 매개변수를 다르게 하고 싶을 때
    // 2. 외부 라이브러리를 bean 으로 등록하고 싶을 때
    public ClassD call() {
        return new ClassD();
    }
}
