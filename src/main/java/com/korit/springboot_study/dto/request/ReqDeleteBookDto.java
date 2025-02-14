package com.korit.springboot_study.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReqDeleteBookDto {
    @ApiModelProperty(value = "책 이름", example = "홍길동전")
    private String bookName;

}
