package com.example.demo.service;

import com.example.demo.model.Lesson;
import com.example.demo.model.LessonAnswer;
import com.example.demo.model.dto.LessonAnswerDTO;

import java.util.List;

public interface LessonAnswerService {
    List<LessonAnswerDTO> getAllLessonAnswerByLessonId(Long LessonId);

    LessonAnswerDTO insertOrUpdate(LessonAnswerDTO lessonAnswerDTO);

    List<LessonAnswerDTO> insertOrUpdate(List<LessonAnswerDTO> lessonAnswerDTOs);

    void deleteAnswerByQuestionId(Long questionId);
}
