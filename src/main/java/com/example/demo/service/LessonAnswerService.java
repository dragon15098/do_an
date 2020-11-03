package com.example.demo.service;

import com.example.demo.model.LessonAnswer;
import com.example.demo.model.dto.LessonAnswerDTO;

import java.util.List;

public interface LessonAnswerService {
    List<LessonAnswerDTO> getAllLessonAnswerByLessonId(Long LessonId);
}
