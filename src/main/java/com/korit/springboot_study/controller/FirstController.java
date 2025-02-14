package com.korit.springboot_study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller     // html, json 응답 처리 가능
public class FirstController {

    @GetMapping("/mvc/hello")
    public String hello(Model model) {
        model.addAttribute("name", "홍길동");
        System.out.println("hello 메소드 호출");
        
        return "/resource/templates/hello.html";
    }

    @GetMapping("/mvc/hello2")
    public String hello2() {
        System.out.println("hello 메소드 호출");

        return "hello";
    }
}
