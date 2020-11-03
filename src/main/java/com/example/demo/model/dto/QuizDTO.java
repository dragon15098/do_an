package com.example.demo.model.dto;

import com.example.demo.model.QuizQuestion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizDTO extends BaseDTO {
    private String quizTitle;
    private List<QuizQuestionDTO> quizQuestions;
}
