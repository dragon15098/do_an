package com.example.demo.service.impl;

import com.example.demo.model.Lesson;
import com.example.demo.model.dto.LessonDTO;
import com.example.demo.model.dto.LessonQuestionDTO;
import com.example.demo.model.helper.LessonHelper;
import com.example.demo.repository.LessonRepository;
import com.example.demo.service.LessonQuestionService;
import com.example.demo.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final LessonQuestionService lessonQuestionService;

    @Override
    public List<LessonDTO> getAllLessonBySectionId(Long sectionId) {
        return lessonRepository.findAllLessonBySectionId(sectionId)
                .stream()
                .map(this::tupleToLessonDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessonDTO getLessonDetailById(Long lessonId) {
        Optional<LessonDTO> result = lessonRepository.findLessonById(lessonId)
                .stream()
                .map(tuple -> {
                    LessonDTO lessonDTO = tupleToLessonDTO(tuple);
                    lessonDTO.setLessonQuestions(getLessonQuestion(lessonDTO));
                    return lessonDTO;
                })
                .findFirst();
        return result.orElse(new LessonDTO());
    }

    private List<LessonQuestionDTO> getLessonQuestion(LessonDTO lessonDTO) {
        return lessonQuestionService.getLessonQuestion(lessonDTO.getId());
    }

    @Override
    public LessonDTO getLessonById(Long lessonId) {
        Optional<LessonDTO> result = lessonRepository.findLessonById(lessonId)
                .stream()
                .map(this::tupleToLessonDTO)
                .findFirst();
        return result.orElse(new LessonDTO());
    }

    @Override
    public LessonDTO getFistLessonIdByCourseId(Long courseId) {
        return lessonRepository.findFirstLessonByCourseId(courseId).stream().map(tuple -> {
            LessonDTO lesson = new LessonDTO();
            lesson.setId((Long) tuple.get("id"));
            return lesson;
        }).findFirst().orElse(new LessonDTO());
    }

    @Override
    public LessonDTO insertOrUpdate(LessonDTO lessonDTO) {
        LessonHelper lessonHelper = new LessonHelper(lessonDTO);
        Lesson lesson = lessonHelper.lessonDTOToLesson();
        lesson = lessonRepository.save(lesson);
        lessonDTO.setId(lesson.getId());
        insertOrUpdateQuestion(lessonDTO, lessonDTO.getLessonQuestions());
        return lessonDTO;
    }

    @Override
    public List<LessonDTO> insertOrUpdate(List<LessonDTO> lessonDTOs) {
        lessonDTOs.forEach(this::insertOrUpdate);
        return lessonDTOs;
    }

    private void insertOrUpdateQuestion(LessonDTO lesson, List<LessonQuestionDTO> lessonQuestions) {
        if (lessonQuestions != null) {
            // fake dto ignore loop for response
            LessonDTO lessonDTO = new LessonDTO();
            lessonDTO.setId(lesson.getId());

            lessonQuestions.forEach(lessonQuestionDTO -> lessonQuestionDTO.setLesson(lessonDTO));
            this.lessonQuestionService.insertOrUpdate(lessonQuestions);
        }
    }

    private LessonDTO tupleToLessonDTO(Tuple tuple) {
        LessonDTO lesson = new LessonDTO();
        lesson.setId((Long) tuple.get("id"));
        lesson.setLessonTitle((String) tuple.get("lessonTitle"));
        lesson.setUrlVideo((String) tuple.get("urlVideo"));
        lesson.setDescription((String) tuple.get("description"));
        return lesson;
    }
}
