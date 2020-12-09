package com.example.demo.model.helper;

import com.example.demo.model.Quiz;
import com.example.demo.model.dto.QuizDTO;

public class QuizHelper {
    private final QuizDTO quizDTO;

    public QuizHelper(QuizDTO quizDTO) {
        this.quizDTO = quizDTO;
    }
    public Quiz quizDTOToQuiz(){
        Quiz quiz = new Quiz();
        quiz.setId(quizDTO.getId());
        quiz.setQuizTitle(quizDTO.getQuizTitle());
        return quiz;
    }
}
