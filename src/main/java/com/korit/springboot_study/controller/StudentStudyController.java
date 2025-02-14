package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.study.ReqAddInstructorDto;
import com.korit.springboot_study.dto.request.study.ReqAddMajorDto;
import com.korit.springboot_study.dto.request.study.ReqUpdateMajorDto;
import com.korit.springboot_study.dto.response.common.ResponseDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.study.Instructor;
import com.korit.springboot_study.entity.study.Major;
import com.korit.springboot_study.mapper.StudentStudyMapper;
import com.korit.springboot_study.service.StudentStudyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@Validated      // 유효성 검사
public class StudentStudyController {

    @Autowired
    private StudentStudyService studentStudyService;

    @GetMapping("/api/study/majors")
    @ApiOperation(value = "학과 전체 조회")
    public ResponseEntity<SuccessResponseDto<List<Major>>> getMajors() throws NotFoundException {
        return ResponseEntity.ok().body(studentStudyService.getMajorsAll());
    }

    @GetMapping("/api/study/instructors")
    @ApiOperation(value = "교수 전체 조회")
    public ResponseEntity<SuccessResponseDto<List<Instructor>>> getInstructor() throws NotFoundException {
        return ResponseEntity.ok().body(studentStudyService.getInstructorsAll());
    }

    @PostMapping("/api/study/major")
    public ResponseEntity<SuccessResponseDto<Major>> addMajor(@Valid @RequestBody ReqAddMajorDto reqAddMajorDto) throws MethodArgumentNotValidException {
        System.out.println(reqAddMajorDto);

//        boolean isNull = reqAddMajorDto == null;
//        boolean isBlank = reqAddMajorDto.getMajorName().isBlank();
//        boolean isNotKor = !Pattern.matches("^[ㄱ-ㅎ|가-힣]*$", reqAddMajorDto.getMajorName());
//
//        if(isNull || isBlank || isNotKor) {
//            BindingResult bindingResult = new BeanPropertyBindingResult(null, "major");
//            bindingResult.addError(new ObjectError("majorName", "학과명은 Null이고 "));
//            throw new MethodArgumentNotValidException(null, bindingResult);
//        }

        return ResponseEntity.ok().body(studentStudyService.addMajor(reqAddMajorDto));
    }

    @PostMapping("/api/study/instructor")
    public ResponseEntity<SuccessResponseDto<Instructor>> addInstructor(@RequestBody ReqAddInstructorDto reqAddInstructorDto) {
        System.out.println(reqAddInstructorDto);
        return ResponseEntity.ok().body(studentStudyService.addInstructor(reqAddInstructorDto));
    }

    @PutMapping("/api/study/major/{majorId}")
    public ResponseEntity<SuccessResponseDto<?>> updateMajor(
            @ApiParam(value = "학과 ID", example = "1", required = true)
            @PathVariable @Min(value = 1, message = "학과 ID는 1 이상의 정수이어야 합니다.") int majorId,
            @Valid @RequestBody ReqUpdateMajorDto reqUpdateMajorDto     // RequestBody -> JSON
    ) throws NotFoundException, MethodArgumentNotValidException {
        return ResponseEntity.ok().body(studentStudyService.modifyMajor(majorId, reqUpdateMajorDto));
    }

}
