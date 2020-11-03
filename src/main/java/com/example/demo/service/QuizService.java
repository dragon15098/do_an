package com.example.demo.service;

import com.example.demo.model.QuizAnswer;
import com.example.demo.model.dto.AnswerResultDTO;
import com.example.demo.model.dto.QuizDTO;

import java.util.List;

public interface QuizService {
    QuizDTO getQuizDetail(Long quizId);

    AnswerResultDTO submitQuiz(Long userCourseId, Long quizId, List<QuizAnswer> quizAnswers);

}
