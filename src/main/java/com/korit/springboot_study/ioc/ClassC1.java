package com.korit.springboot_study.ioc;

import org.springframework.stereotype.Component;

@Component(value = "c1")
public class ClassC1 implements ClassC {
    @Override
    public void classCallC() {
        System.out.println("ClassC1 호출");
    }
}
