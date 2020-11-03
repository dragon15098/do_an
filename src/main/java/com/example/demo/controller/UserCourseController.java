package com.example.demo.controller;

import com.example.demo.model.UserCourse;
import com.example.demo.model.dto.UserCourseDTO;
import com.example.demo.service.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user_course")
public class UserCourseController {
    @Autowired
    UserCourseService userCourseService;

    @PostMapping("/buy_course")
    public ResponseEntity<UserCourse> buyCourse(@RequestBody UserCourse userCourse) {
        userCourse = userCourseService.insert(userCourse);
        return new ResponseEntity<>(userCourse, HttpStatus.OK);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<UserCourseDTO>> getAll() {
        List<UserCourseDTO> userCourses = userCourseService.getAllByUser();
        return new ResponseEntity<>(userCourses, HttpStatus.OK);
    }
}
