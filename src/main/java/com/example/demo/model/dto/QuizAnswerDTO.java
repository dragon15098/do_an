package com.example.demo.model.dto;

import com.example.demo.model.QuizQuestion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizAnswerDTO extends BaseDTO {
    private String content;
    private QuizQuestionDTO quizQuestion;
}
