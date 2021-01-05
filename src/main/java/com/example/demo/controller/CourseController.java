package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.dto.CourseDTO;
import com.example.demo.model.dto.Page;
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
    public ResponseEntity<Page<CourseDTO>> getCourseHottest(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(courseService.getCourseHottest(pageNumber, pageSize), HttpStatus.OK);
    }


    @GetMapping("/get_course_type_2")
    public ResponseEntity<Page<CourseDTO>> getCourseNewest(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(courseService.getCourseNewest(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/get_course_type_3")
    public ResponseEntity<Page<CourseDTO>> getSomeRandomCourse(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(courseService.getSomeRandomCourse(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CourseDTO>> findCourse(String courseName) {
        return new ResponseEntity<>(courseService.findCourseByTitle(courseName), HttpStatus.OK);
    }

    @GetMapping("/get_course_by_filter")
    public ResponseEntity<List<CourseDTO>> searchCourse(@RequestParam("categories") List<Long> categoryId,
                                                        @RequestParam("name") String courseName) {
        return new ResponseEntity<>(courseService.getCourseByFilter(categoryId, courseName), HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<Page<CourseDTO>> search(
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @RequestParam(value = "courseName", required = false) String title,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "createFrom", required = false) String fromDate,
            @RequestParam(value = "createTo", required = false) String toDate,
            @RequestParam(value = "instructor", required = false) String instructorName,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        return new ResponseEntity<>(courseService.searchCourse(categoryId, title, status, fromDate, toDate, instructorName, pageSize, pageNumber), HttpStatus.OK);
    }

    @PostMapping("/approve")
    public ResponseEntity<CourseDTO> approveCourse(@RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.approveCourse(courseDTO), HttpStatus.OK);
    }
}
