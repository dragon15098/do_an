package com.example.demo.controller;

import com.example.demo.model.dto.AnswerResultDTO;
import com.example.demo.model.dto.LessonAnswerDTO;
import com.example.demo.model.dto.LessonQuestionDTO;
import com.example.demo.service.LessonQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/lesson_question")
public class LessonQuestionController {
    @Autowired
    LessonQuestionService lessonQuestionService;

    @GetMapping("/get/{lessonId}")
    public ResponseEntity<List<LessonQuestionDTO>> getAllLessonNote(@PathVariable Long lessonId) {
        return new ResponseEntity<>(lessonQuestionService.getLessonQuestion(lessonId), HttpStatus.OK);
    }

    @PostMapping("/check_lesson/{lessonId}/{courseId}")
    public ResponseEntity<AnswerResultDTO> checkLessonQuestion(@PathVariable Long courseId, @PathVariable Long lessonId, @RequestBody List<LessonAnswerDTO> lessonAnswers) {
        return new ResponseEntity<>(lessonQuestionService.checkLessonQuestion(courseId, lessonId, lessonAnswers), HttpStatus.OK);
    }
}
