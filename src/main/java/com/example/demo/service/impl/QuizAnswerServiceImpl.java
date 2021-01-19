package com.example.demo.service.impl;

import com.example.demo.model.QuizAnswer;
import com.example.demo.model.dto.QuizAnswerDTO;
import com.example.demo.model.helper.QuizAnswerHelper;
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

    @Override
    public QuizAnswerDTO insertOrUpdate(QuizAnswerDTO quizAnswerDTO) {
        QuizAnswerHelper quizAnswerHelper = new QuizAnswerHelper(quizAnswerDTO);
        QuizAnswer quizAnswer = quizAnswerHelper.quizAnswerDTOToQuizAnswer();
        quizAnswer = quizAnswerRepository.save(quizAnswer);
        quizAnswerDTO.setId(quizAnswer.getId());
        return quizAnswerDTO;
    }

    @Override
    public List<QuizAnswerDTO> insertOrUpdate(List<QuizAnswerDTO> quizAnswerDTOs) {
        quizAnswerDTOs.forEach(this::insertOrUpdate);
        return quizAnswerDTOs;
    }

    @Override
    public void deleteAnswerByQuestionId(Long questionId) {
        quizAnswerRepository.deleteQuizAnswerByQuizQuestionId(questionId);
    }

}
