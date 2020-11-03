package com.example.demo.service.impl;

import com.example.demo.model.QuizAnswer;
import com.example.demo.model.dto.QuizAnswerDTO;
import com.example.demo.repository.QuizAnswerRepository;
import com.example.demo.service.QuizAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizAnswerServiceImpl implements QuizAnswerService {
    private final QuizAnswerRepository quizAnswerRepository;

    @Override
    public List<QuizAnswerDTO> getAllQuizAnswerByQuestionId(Long quizQuestionId) {
        return quizAnswerRepository.getAllByQuestionId(quizQuestionId).stream().map(tuple -> {
            QuizAnswerDTO quizAnswer = new QuizAnswerDTO();
            quizAnswer.setId((Long) tuple.get("id"));
            quizAnswer.setContent((String) tuple.get("content"));
            return quizAnswer;
        }).collect(Collectors.toList());
    }
}
