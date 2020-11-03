package com.example.demo.service;

import com.example.demo.model.StudentDetail;
import com.example.demo.model.dto.StudentDetailDTO;

public interface StudentDetailService {
    StudentDetailDTO getDetailById(Long studentId);
}
