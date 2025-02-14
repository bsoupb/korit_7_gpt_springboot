package com.korit.springboot_study.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClassA {
    @Autowired
    private ClassD d;

    public void classCallA() {
        System.out.println("ClassA 메소드 호출");
    }
}
