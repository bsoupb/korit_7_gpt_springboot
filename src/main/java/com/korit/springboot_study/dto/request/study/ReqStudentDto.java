package com.korit.springboot_study.dto.request.study;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Getter
@Setter
//@RequiredArgsConstructor    // + final
//@AllArgsConstructor     // 기본 생성자 kill -> 모두 required (필수값 정의)
@ApiModel(description = "학생정보 조회 학습 DTO")
public class ReqStudentDto {
    @NonNull    // 빈값 X
    @ApiModelProperty(value = "학생 이름", example = "홍길동", required=true)
    private String name;
    @ApiModelProperty(value = "학생 나이", example = "30", required=false)
    private int age;
    // 생성해도 setter가 없으면 값은 변경되지 않음
    // Setter 없으면 값이 없음
    // 기본 생성자 -> 빈값일 수 있음
}
