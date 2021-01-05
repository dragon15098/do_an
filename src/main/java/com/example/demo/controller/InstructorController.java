package com.example.demo.controller;

import com.example.demo.model.dto.InstructorDetailDTO;
import com.example.demo.service.InstructorDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/instructor")
public class InstructorController {

    @Autowired
    InstructorDetailService instructorDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDetailDTO> getInstructorDetail(@PathVariable Long id) {
        return new ResponseEntity<>(instructorDetailService.getInstructorDetail(id), HttpStatus.OK);
    }
}
