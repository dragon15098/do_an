package com.example.demo.service.impl;

import com.example.demo.model.Quiz;
import com.example.demo.model.Section;
import com.example.demo.model.dto.LessonDTO;
import com.example.demo.model.dto.QuizDTO;
import com.example.demo.model.dto.SectionDTO;
import com.example.demo.repository.QuizRepository;
import com.example.demo.repository.SectionRepository;
import com.example.demo.service.LessonService;
import com.example.demo.service.QuizQuestionService;
import com.example.demo.service.QuizService;
import com.example.demo.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    @Autowired
    private final SectionRepository sectionRepository;
    @Autowired
    private final QuizRepository quizRepository;

    @Autowired
    private final QuizQuestionService quizQuestionService;

    @Autowired
    private final LessonService lessonService;


    @Override
    public List<SectionDTO> getCourseSection(Long courseId) {
        return sectionRepository.getAllSectionByCourseId(courseId).stream().map(tuple -> {
            SectionDTO section = tupleToDTO(tuple);
            List<LessonDTO> lessons = lessonService.getAllLessonBySectionId(section.getId());
            section.setLessons(lessons);
            return section;
        }).collect(Collectors.toList());
    }

    @Override
    public SectionDTO getSectionDetail(Long sectionId) {
        return sectionRepository.getSectionBySectionId(sectionId).stream()
                .map(tuple -> {
                    SectionDTO section = tupleToDTO(tuple);
                    List<LessonDTO> lessons = lessonService.getAllLessonBySectionId(section.getId());
                    section.setLessons(lessons);
                    section.setQuiz(getQuizDetail(section));
                    return section;
                })
                .findFirst()
                .orElse(new SectionDTO());
    }

    private QuizDTO getQuizDetail(SectionDTO sectionDTO) {
        Long quizId = sectionDTO.getQuiz().getId();
         return quizRepository.getQuizDetail(quizId).stream().map(tuple -> {
            QuizDTO quiz = new QuizDTO();
            quiz.setId((Long) tuple.get("id"));
            quiz.setQuizTitle((String) tuple.get("quizTitle"));
            quiz.setQuizQuestions(quizQuestionService.getAllQuizQuestionByQuizId(quizId));
            return quiz;
        }).findFirst().orElse(new QuizDTO());
    }

    private SectionDTO tupleToDTO(Tuple tuple) {
        SectionDTO section = new SectionDTO();
        section.setId((Long) tuple.get("id"));
        section.setSectionTitle((String) tuple.get("sectionTitle"));
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setId((Long) tuple.get("quizId"));
        section.setQuiz(quizDTO);
        return section;
    }

}
