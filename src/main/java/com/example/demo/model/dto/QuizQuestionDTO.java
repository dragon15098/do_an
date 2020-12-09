package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizQuestionDTO extends BaseDTO {
    private String questionTitle;
    private String question;
    private QuizAnswerDTO correctAnswer;
    private QuizDTO quiz;
    private List<QuizAnswerDTO> quizAnswers;
    private QuizQuestionStatus quizQuestionStatus = QuizQuestionStatus.NOT_ANSWERED;
    private int correctAnswerPosition;
    public enum QuizQuestionStatus {
        ERROR, SUCCESS, NOT_ANSWERED
    }
}
