package com.example.demo.service.impl;

import com.example.demo.model.dto.QuizAnswerDTO;
import com.example.demo.model.dto.QuizQuestionDTO;
import com.example.demo.repository.QuizQuestionRepository;
import com.example.demo.service.QuizAnswerService;
import com.example.demo.service.QuizQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizQuestionServiceImpl implements QuizQuestionService {
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizAnswerService quizAnswerService;

    @Override
    public List<QuizQuestionDTO> getAllQuizQuestionByQuizId(Long quizId) {
        return quizQuestionRepository.getAllQuizQuestionByQuizId(quizId).stream().map(this::tupleToQuizQuestionDTO).collect(Collectors.toList());
    }

    @Override
    public List<QuizQuestionDTO> getQuizQuestionWithCorrectAnswer(Long quizId) {
        return quizQuestionRepository.getAllQuizQuestionByQuizId(quizId).stream().map(tuple -> {
            QuizQuestionDTO quizQuestion = new QuizQuestionDTO();
            quizQuestion.setId((Long) tuple.get("id"));
            quizQuestion.setQuestion((String) tuple.get("question"));
            quizQuestion.setQuizAnswers(quizAnswerService.getAllQuizAnswerByQuestionId(quizQuestion.getId()));
            quizQuestion.setQuestionTitle((String) tuple.get("questionTitle"));
            QuizAnswerDTO quizAnswer = new QuizAnswerDTO();
            quizAnswer.setId(Long.valueOf((Integer) tuple.get("correctAnswerId")));
            quizQuestion.setCorrectAnswer(quizAnswer);
            return quizQuestion;
        }).collect(Collectors.toList());
    }

    private QuizQuestionDTO tupleToQuizQuestionDTO(Tuple tuple) {
        QuizQuestionDTO quizQuestion = new QuizQuestionDTO();
        quizQuestion.setId((Long) tuple.get("id"));
        quizQuestion.setQuestion((String) tuple.get("question"));
        quizQuestion.setQuestionTitle((String) tuple.get("questionTitle"));
        quizQuestion.setQuizAnswers(quizAnswerService.getAllQuizAnswerByQuestionId(quizQuestion.getId()));
        return quizQuestion;
    }
}
