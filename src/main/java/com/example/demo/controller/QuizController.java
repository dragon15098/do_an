package com.example.demo.controller;

import com.example.demo.model.Quiz;
import com.example.demo.model.QuizAnswer;
import com.example.demo.model.QuizQuestion;
import com.example.demo.model.dto.AnswerResultDTO;
import com.example.demo.model.dto.QuizDTO;
import com.example.demo.model.dto.QuizQuestionDTO;
import com.example.demo.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/quiz")
public class QuizController {
    @Autowired
    QuizService quizService;


    @GetMapping("/get/{quizId}")
    public ResponseEntity<QuizDTO> getAllLessonNote(@PathVariable Long quizId) {
        return new ResponseEntity<>(quizService.getQuizDetail(quizId), HttpStatus.OK);
    }

    @PostMapping("/submit/{userCourseId}/{quizId}")
    public ResponseEntity<AnswerResultDTO> submitAnswer(@PathVariable Long userCourseId, @PathVariable Long quizId, @RequestBody List<QuizAnswer> quizAnswers) {
        return new ResponseEntity<>(quizService.submitQuiz(userCourseId, quizId, quizAnswers), HttpStatus.OK);
    }

}
