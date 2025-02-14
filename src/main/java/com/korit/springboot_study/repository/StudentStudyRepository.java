package com.korit.springboot_study.repository;

import com.korit.springboot_study.entity.study.Instructor;
import com.korit.springboot_study.entity.study.Major;
import com.korit.springboot_study.exception.CustomDuplicateKeyException;
import com.korit.springboot_study.mapper.StudentStudyMapper;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// DB에서 오늘 오류들 가공
@Repository
public class StudentStudyRepository {
    @Autowired
    private StudentStudyMapper studentStudyMapper;

    public Optional<List<Major>> findMajorAll() {
        List<Major> foundMajors = studentStudyMapper.selectMajorsAll();

//        if(foundMajors.isEmpty()) {
//            return Optional.empty();
//        }

        return foundMajors.isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(foundMajors);
    }
    // Optional: 값이 null이 있는지 확인하려고 사용
    // 비어있는 리스트는 Optional에서 null이 아님(주소값이 있기 때문)
    // of(): 절대 null이 아닐 때, ofNullable(): null도 허용

    public Optional<List<Instructor>> findInstructorAll() {
        List<Instructor> foundInstructors = studentStudyMapper.selectInstructorsAll();

        return foundInstructors.isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(foundInstructors);
    }

    public Optional<Major> saveMajor(Major major) throws DuplicateKeyException {
        try {
            studentStudyMapper.insertMajor(major);
        } catch (DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                    e.getMessage(),
                    Map.of("majorName", "이미 존재하는 학과명입니다."));
        }

        return Optional.ofNullable(new Major(major.getMajorId(), major.getMajorName()));
    }

    public Optional<Instructor> saveInstructor(Instructor instructor) throws DuplicateKeyException {
        try {
            studentStudyMapper.insertInstructor(instructor);
        } catch(DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                    e.getMessage(),
                    Map.of("instructorName", "이미 존재하는 교수명입니다."));
        }

        return Optional.ofNullable(new Instructor(instructor.getInstructorId(), instructor.getInstructorName()));
    }

    public Optional<Major> updateMajor(Major major) throws NotFoundException, DuplicateKeyException {
        try {
            if(studentStudyMapper.updateMajorName(major) < 1) {
                return Optional.empty();
            }
        } catch (DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                    e.getMessage(),
                    Map.of("majorName", "이미 존재하는 학과명입니다.")
            );
        }

        return Optional.ofNullable(major);
    }
}
