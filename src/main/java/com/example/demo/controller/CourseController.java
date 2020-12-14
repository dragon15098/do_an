package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.dto.CourseDTO;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseDetailById(@PathVariable Long id) {
        CourseDTO courseDTO = courseService.getCourseById(id);
        return new ResponseEntity<>(courseDTO, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        courseDTO = courseService.insertOrUpdate(courseDTO);
        return new ResponseEntity<>(courseDTO, HttpStatus.OK);
    }

    @GetMapping("/create_by/{id}")
    public ResponseEntity<List<CourseDTO>> getCourseCreateBy(@PathVariable Long id) {
        List<CourseDTO> courses = courseService.getCourseCreateByUserId(id);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<Course>> getAll() {
        return new ResponseEntity<>(courseService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get_course_type_1")
    public ResponseEntity<List<CourseDTO>> getCourseHottest() {
        return new ResponseEntity<>(courseService.getCourseHottest(), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CourseDTO>> findCourse(String courseName) {
        return new ResponseEntity<>(courseService.findCourseByTitle(courseName), HttpStatus.OK);
    }

    @PostMapping("/approve")
    public ResponseEntity<CourseDTO> approveCourse(@RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.approveCourse(courseDTO), HttpStatus.OK);
    }
}
