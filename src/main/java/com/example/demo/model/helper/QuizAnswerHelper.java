package com.example.demo.model.helper;

import com.example.demo.model.Quiz;
import com.example.demo.model.QuizAnswer;
import com.example.demo.model.dto.QuizAnswerDTO;

public class QuizAnswerHelper {
    private final QuizAnswerDTO quizAnswerDTO;

    public QuizAnswerHelper(QuizAnswerDTO quizAnswerDTO) {
        this.quizAnswerDTO = quizAnswerDTO;
    }

    public QuizAnswer quizAnswerDTOToQuizAnswer() {
        QuizAnswer quizAnswer = new QuizAnswer();
        quizAnswer.setContent(quizAnswerDTO.getContent());
        quizAnswer.setQuizQuestionId(quizAnswerDTO.getQuizQuestion().getId());
        quizAnswer.setId(quizAnswerDTO.getId());
        return quizAnswer;
    }
}
