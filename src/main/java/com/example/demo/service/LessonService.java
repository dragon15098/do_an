package com.example.demo.service;

import com.example.demo.model.Lesson;
import com.example.demo.model.dto.LessonDTO;

import java.util.List;

public interface LessonService {
    List<LessonDTO> getAllLessonBySectionId(Long sectionId);

    LessonDTO getLessonDetailById(Long lessonId);

    LessonDTO getLessonById(Long lessonId);

    LessonDTO getFistLessonIdByCourseId(Long courseId);
}
