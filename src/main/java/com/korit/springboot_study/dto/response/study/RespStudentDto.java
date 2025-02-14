package com.korit.springboot_study.dto.response.study;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(description = "학생정보 조회 학습 응답 DTO")
public class RespStudentDto {
    @ApiModelProperty(value = "학생 ID", example = "1")
    private int id;
    @ApiModelProperty(value = "학생 이름", example = "홍길동")
    private String name;
    @ApiModelProperty(value = "학생 나이", example = "30")
    private int age;
}
