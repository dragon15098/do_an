package com.example.demo.service.impl;

import com.example.demo.model.dto.LessonDTO;
import com.example.demo.repository.LessonRepository;
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

    private LessonDTO tupleToLessonDTO(Tuple tuple) {
        LessonDTO lesson = new LessonDTO();
        lesson.setId((Long) tuple.get("id"));
        lesson.setLessonTitle((String) tuple.get("lessonTitle"));
        lesson.setUrlVideo((String) tuple.get("urlVideo"));
        lesson.setDescription((String) tuple.get("description"));
        return lesson;
    }
}
