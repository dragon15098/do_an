package com.example.demo.model.helper;

import com.example.demo.model.Quiz;
import com.example.demo.model.QuizQuestion;
import com.example.demo.model.dto.QuizAnswerDTO;
import com.example.demo.model.dto.QuizQuestionDTO;

public class QuizQuestionHelper {
    private final QuizQuestionDTO quizQuestionDTO;

    public QuizQuestionHelper(QuizQuestionDTO quizQuestionDTO) {
        this.quizQuestionDTO = quizQuestionDTO;
    }

    public QuizQuestion quizQuestionDTOToQuizQuestion() {
        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setId(quizQuestionDTO.getId());
        quizQuestion.setQuizId(quizQuestionDTO.getQuiz().getId());
        QuizAnswerDTO correctAnswer = quizQuestionDTO.getCorrectAnswer();
        if (correctAnswer != null) {
            quizQuestion.setCorrectAnswerId(correctAnswer.getId());
        }
        quizQuestion.setQuestion(quizQuestionDTO.getQuestion());
        quizQuestion.setQuestionTitle(quizQuestionDTO.getQuestionTitle());
        return quizQuestion;
    }
}
