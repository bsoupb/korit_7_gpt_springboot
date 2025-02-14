package com.korit.springboot_study.entity.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Instructor {
    private int instructorId;
    private String instructorName;
}
