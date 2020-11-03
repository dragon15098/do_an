package com.example.demo.service.impl;

import com.example.demo.model.dto.LessonDTO;
import com.example.demo.model.dto.QuizDTO;
import com.example.demo.model.dto.SectionDTO;
import com.example.demo.repository.SectionRepository;
import com.example.demo.service.LessonService;
import com.example.demo.service.QuizService;
import com.example.demo.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    @Autowired
    private final SectionRepository sectionRepository;

    @Autowired
    private final LessonService lessonService;

    @Override
    public List<SectionDTO> getCourseSection(Long courseId) {
        return sectionRepository.getAllSectionByCourseId(courseId).stream().map(tuple -> {
            SectionDTO section = new SectionDTO();
            section.setId((Long) tuple.get("id"));
            section.setSectionTitle((String) tuple.get("sectionTitle"));
            List<LessonDTO> lessons = lessonService.getAllLessonBySectionId(section.getId());
            section.setLessons(lessons);
            Long quizId = (Long) tuple.get("quizId");
            if (quizId != null) {
                QuizDTO quiz = new QuizDTO();
                quiz.setId((Long) tuple.get("quizId"));
                quiz.setQuizTitle((String) tuple.get("quizTitle"));
                section.setQuiz(quiz);
            }
            return section;
        }).collect(Collectors.toList());
    }
}
