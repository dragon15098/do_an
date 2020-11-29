package com.example.demo.controller;

import com.example.demo.model.dto.SectionDTO;
import com.example.demo.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/section")
public class SectionController {
    @Autowired
    SectionService sectionService;


    /*
       return all section detail(quiz and lesson)
     */
    @GetMapping("/get_course_section/{id}") 
    public ResponseEntity<List<SectionDTO>> getCourseSection(@PathVariable Long id) {
        return new ResponseEntity<>(sectionService.getCourseSection(id), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SectionDTO> getSectionDetail(@PathVariable Long id) {
        return new ResponseEntity<>(sectionService.getSectionDetail(id), HttpStatus.OK);
    }
}
