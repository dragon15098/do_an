package com.example.demo.model.helper;

import com.example.demo.model.StudentDetail;
import com.example.demo.model.dto.StudentDetailDTO;

public class StudentDetailHelper {
    private final StudentDetailDTO studentDetailDTO;


    public StudentDetailHelper(StudentDetailDTO studentDetailDTO) {
        this.studentDetailDTO = studentDetailDTO;
    }

    public StudentDetail studentDetailDTOToStudentDetail() {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setGrade(studentDetailDTO.getGrade());
        studentDetail.setSchool(studentDetailDTO.getSchool());
        return studentDetail;
    }
}
