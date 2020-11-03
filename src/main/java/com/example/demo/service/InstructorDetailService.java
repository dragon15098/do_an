package com.example.demo.service;

import com.example.demo.model.InstructorDetail;
import com.example.demo.model.dto.InstructorDetailDTO;

public interface InstructorDetailService {
    InstructorDetailDTO getInstructorDetail(Long instructorId);
}
