package com.example.demo.controller;

import com.example.demo.model.dto.CourseDTO;
import com.example.demo.repository.QuizQuestionRepository;
import com.example.demo.service.CourseService;
import com.example.demo.service.QuizQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz_question")
public class QuizQuestionController {
    @Autowired
    private QuizQuestionService quizQuestionService;


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> getCourseDetailById(@PathVariable Long id) {
        quizQuestionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
