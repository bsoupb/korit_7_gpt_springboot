package com.korit.springboot_study.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PrintParamsAspect {

    private static final Logger log = LoggerFactory.getLogger(PrintParamsAspect.class);

    @Pointcut("@annotation(com.korit.springboot_study.aspect.annotation.PrintParamsAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        log.info("로그를 출력합니다.");

        for(int i = 0; i < parameterNames.length; i++) {
            System.out.println(parameterNames[i] + ": " + args[i]);
        }

        Object result = joinPoint.proceed();

        return result;
    }
}
