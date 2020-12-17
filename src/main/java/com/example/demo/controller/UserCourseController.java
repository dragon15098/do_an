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
    public ResponseEntity<UserCourseDTO> buyCourse(@RequestBody UserCourseDTO userCourseDTO) {
        userCourseDTO = userCourseService.insertOrUpdate(userCourseDTO);
        return new ResponseEntity<>(userCourseDTO, HttpStatus.OK);
    }

    @PostMapping("/rating")
    public ResponseEntity<UserCourseDTO> ratingCourse (@RequestBody UserCourseDTO userCourseDTO) {
        userCourseDTO = userCourseService.ratingCourse(userCourseDTO);
        return new ResponseEntity<>(userCourseDTO, HttpStatus.OK);
    }


    @GetMapping("/get_all")
    public ResponseEntity<List<UserCourseDTO>> getAll() {
        List<UserCourseDTO> userCourses = userCourseService.getAllByUser();
        return new ResponseEntity<>(userCourses, HttpStatus.OK);
    }

    @GetMapping("/get/{courseId}")
    public ResponseEntity<UserCourseDTO> getUserCourseByCourseId(@PathVariable("courseId") Long courseId) {
        UserCourseDTO userCourse = userCourseService.getUserCourseByCourseId(courseId);
        return new ResponseEntity<>(userCourse, HttpStatus.OK);
    }

    @GetMapping("/get_comment/{courseId}")
    public ResponseEntity<List<UserCourseDTO>> getUserComments(@PathVariable("courseId") Long courseId) {
        List<UserCourseDTO> userCourse = userCourseService.getUserComments(courseId);
        return new ResponseEntity<>(userCourse, HttpStatus.OK);
    }
}
