package com.example.demo.controller;

import com.example.demo.model.CoursePrize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course_prize")
public class CoursePrizeController {


    @GetMapping("/{id}")
    public ResponseEntity<CoursePrize> insert(@PathVariable String id) {
        CoursePrize course = new CoursePrize();
        course.setPrice(100f);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
}
