package com.korit.springboot_study.controller.advice;

import com.korit.springboot_study.dto.response.common.BadRequestResponseDto;
import com.korit.springboot_study.dto.response.common.NotFoundResponseDto;
import com.korit.springboot_study.exception.CustomDuplicateKeyException;
import org.apache.ibatis.javassist.NotFoundException;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<NotFoundResponseDto<?>> notFound(NotFoundException e) {
        return ResponseEntity.status(404).body(new NotFoundResponseDto<>(e.getMessage()));
    }

    @ExceptionHandler(value = CustomDuplicateKeyException.class)
    public ResponseEntity<BadRequestResponseDto<?>> duplicateKey(CustomDuplicateKeyException e) {
        return ResponseEntity.status(400).body(new BadRequestResponseDto<>(e.getErrors()));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<BadRequestResponseDto<?>> signinError(AuthenticationException e) {
        return ResponseEntity.status(403).body(new BadRequestResponseDto<>(e.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestResponseDto<?>> validation(MethodArgumentNotValidException e) {
        List<Map<String, String>> errorMap = null;
        BindingResult bindingResult = e.getBindingResult();
        if(bindingResult.hasErrors()) {
            errorMap = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> Map.of(fieldError.getField(), fieldError.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
        return ResponseEntity.status(400).body(new BadRequestResponseDto<>(errorMap));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<BadRequestResponseDto<?>> validation(ConstraintViolationException e) {
        return ResponseEntity.status(400).body(new BadRequestResponseDto<>(
                e.getConstraintViolations()
                        .stream()
                        .map(constraintViolation -> Map.of(constraintViolation.getPropertyPath(), constraintViolation.getMessage()))
                        .collect(Collectors.toList())
        ));
    }
}
