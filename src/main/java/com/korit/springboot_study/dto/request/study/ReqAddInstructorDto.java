package com.korit.springboot_study.dto.request.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReqAddInstructorDto {
    @ApiModelProperty(value = "교수명", example = "김 교수", required = true)
    private String instructorName;
}
