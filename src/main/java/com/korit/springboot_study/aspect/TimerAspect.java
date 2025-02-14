package com.korit.springboot_study.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimerAspect {
    @Pointcut("execution(* com.korit.springboot_study.service.PostService.getPostById(..))")
    private void executionPointCut() {}

    @Pointcut("@annotation(com.korit.springboot_study.aspect.annotation.TimerAop)")
    private void annotationPointCut() {}

    @Around("executionPointCut()||annotationPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();    // 실제 method 동작호출
        stopWatch.stop();
        System.out.println("메소드 실행시간: " + stopWatch.getTotalTimeSeconds());
        return result;
    }
}
