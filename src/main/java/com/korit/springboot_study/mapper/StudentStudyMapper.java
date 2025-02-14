package com.korit.springboot_study.mapper;

import com.korit.springboot_study.entity.study.Instructor;
import com.korit.springboot_study.entity.study.Major;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentStudyMapper {
    List<Major> selectMajorsAll();
    List<Instructor> selectInstructorsAll();

    int insertMajor(Major major);
    int insertInstructor(Instructor instructor);

    int updateMajorName(Major major);
}
