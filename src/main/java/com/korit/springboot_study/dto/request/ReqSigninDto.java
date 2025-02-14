package com.korit.springboot_study.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ReqSigninDto {
    @ApiModelProperty(value = "사용자이름", example = "user1234", required = true)
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,16}$", message = "영어 대소문자, 숫자, 밑줄(_)만 사용 가능")
    private String username;

    @ApiModelProperty(value = "비밀번호", example = "User12345678!", required = true)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{8,20}$", message = "영어 대소문자, 숫자, 특수문자를 하나 이상 모두 포함하며 8자리 이상 입력해야 합니다.")
    private String password;
}
