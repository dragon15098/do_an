package com.example.demo.service;

import com.example.demo.model.dto.QuizQuestionDTO;

import java.util.List;

public interface QuizQuestionService {
    List<QuizQuestionDTO> getAllQuizQuestionByQuizId(Long quizId);

    List<QuizQuestionDTO> getQuizQuestionWithCorrectAnswer(Long quizId);

    List<QuizQuestionDTO> insertOrUpdate(List<QuizQuestionDTO> quizQuestionDTOs);
}
