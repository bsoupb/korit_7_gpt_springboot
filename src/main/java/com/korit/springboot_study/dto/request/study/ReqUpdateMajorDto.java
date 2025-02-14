package com.korit.springboot_study.dto.request.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ReqUpdateMajorDto {
    @ApiModelProperty(value = "학과명", example = "컴퓨터공학과", required = true)
    @Pattern(regexp = "^[가-힣]+$", message = "학과명은 반드시 한글만 입력 가능하며, 공백이나 숫자, 특수문자는 사용할 수 없습니다.")
    private String majorName;
}
