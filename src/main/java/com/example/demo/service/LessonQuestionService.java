package com.example.demo.service;

import com.example.demo.model.LessonQuestion;
import com.example.demo.model.dto.AnswerResultDTO;
import com.example.demo.model.dto.LessonAnswerDTO;
import com.example.demo.model.dto.LessonQuestionDTO;

import java.util.List;

public interface LessonQuestionService {
    List<LessonQuestionDTO> getLessonQuestion(Long lessonId);

    AnswerResultDTO checkLessonQuestion(Long userCourseId, Long lessonId, List<LessonAnswerDTO> answerResults);

    LessonQuestionDTO insertOrUpdate(LessonQuestionDTO lessonQuestionDTO);

    List<LessonQuestionDTO> insertOrUpdate(List<LessonQuestionDTO> lessonQuestionDTOs);

    void deleteLessonQuestionById(Long id);
}
