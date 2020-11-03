package com.example.demo.service.impl;

import com.example.demo.model.dto.LessonAnswerDTO;
import com.example.demo.repository.LessonAnswerRepository;
import com.example.demo.service.LessonAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonAnswerServiceImpl implements LessonAnswerService {
    public final LessonAnswerRepository lessonAnswerRepository;

    @Override
    public List<LessonAnswerDTO> getAllLessonAnswerByLessonId(Long lessonQuestionId) {
        return lessonAnswerRepository.findAllByLessonQuestionId(lessonQuestionId).stream().map(tuple -> {
                    LessonAnswerDTO lessonAnswerDTO = new LessonAnswerDTO();
                    lessonAnswerDTO.setId((Long) tuple.get("id"));
                    lessonAnswerDTO.setContent((String) tuple.get("content"));
                    lessonAnswerDTO.setExplanation((String) tuple.get("explanation"));
                    return lessonAnswerDTO;
                }
        ).collect(Collectors.toList());
    }
}
