package com.example.demo.model.helper;

import com.example.demo.model.LessonQuestion;
import com.example.demo.model.dto.LessonQuestionDTO;

public class LessonQuestionHelper {
    private final LessonQuestionDTO lessonQuestionDTO;

    public LessonQuestionHelper(LessonQuestionDTO lessonQuestionDTO) {
        this.lessonQuestionDTO = lessonQuestionDTO;
    }

    public LessonQuestion lessonQuestionDTOToLessonQuestion() {
        LessonQuestion lessonQuestion = new LessonQuestion();
        lessonQuestion.setId(this.lessonQuestionDTO.getId());
        lessonQuestion.setLessonId(this.lessonQuestionDTO.getLesson().getId());
        lessonQuestion.setQuestion(this.lessonQuestionDTO.getQuestion());
        lessonQuestion.setQuestionTitle(this.lessonQuestionDTO.getQuestionTitle());
        if (this.lessonQuestionDTO.getCorrectAnswer() != null) {
            lessonQuestion.setCorrectAnswerId(this.lessonQuestionDTO.getCorrectAnswer().getId());
        }
        return lessonQuestion;
    }
}
