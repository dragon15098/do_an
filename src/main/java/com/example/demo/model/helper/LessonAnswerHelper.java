package com.example.demo.model.helper;

import com.example.demo.model.LessonAnswer;
import com.example.demo.model.dto.LessonAnswerDTO;

public class LessonAnswerHelper {
    private final LessonAnswerDTO lessonAnswerDTO;

    public LessonAnswerHelper(LessonAnswerDTO lessonAnswerDTO) {
        this.lessonAnswerDTO = lessonAnswerDTO;
    }

    public LessonAnswer lessonAnswerDTOToLessonAnswer() {
        LessonAnswer lessonAnswer = new LessonAnswer();
        lessonAnswer.setId(lessonAnswerDTO.getId());
        lessonAnswer.setContent(lessonAnswerDTO.getContent());
        lessonAnswer.setLessonQuestionId(lessonAnswerDTO.getLessonQuestion().getId());
        return lessonAnswer;
    }
}
