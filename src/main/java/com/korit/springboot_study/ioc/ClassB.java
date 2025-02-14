package com.korit.springboot_study.ioc;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class ClassB {

//  private final ClassC c1;
    @Qualifier("c1")   // 변수명 맞춰지지 않았을 때
    @Autowired
    private ClassC c1;

    @Qualifier("c2")
    @Autowired
    private ClassC c2;

    public void classCallB() {
        System.out.println("ClassB 메소드 호출");
    }
}
