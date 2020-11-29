package com.example.demo.model.helper;

import com.example.demo.model.Quiz;
import com.example.demo.model.QuizQuestion;
import com.example.demo.model.dto.QuizQuestionDTO;

public class QuizQuestionHelper {
    private final QuizQuestionDTO quizQuestionDTO;
    public QuizQuestionHelper(QuizQuestionDTO quizQuestionDTO){
        this.quizQuestionDTO = quizQuestionDTO;
    }
    public QuizQuestion quizQuestionDTOToQuizQuestion(){
        QuizQuestion quizQuestion = new QuizQuestion();

        return quizQuestion;
    }
}
