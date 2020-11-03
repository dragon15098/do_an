package com.example.demo.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResultDTO extends BaseDTO{
    private List<LessonQuestionDTO> lessonQuestions;
    private List<QuizQuestionDTO> quizQuestions;
    private Boolean totalResult;
}
